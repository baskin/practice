package com.garg.stats.impl.safe.periodic;

import java.util.concurrent.atomic.AtomicInteger;

import com.garg.stats.MergeableStatistics;
import com.garg.stats.ReusableStatistics;
import com.garg.stats.Statistics;
import com.garg.stats.impl.SimpleMergeableStatistics;
import com.garg.stats.impl.SimpleReusableStatistics;
import com.garg.stats.impl.safe.SynchronizedStatistics;

/**
 * A thread safe implementation of <code>Statistics</code> that increases
 * support for concurrent writers by striping the contention on writes. The
 * event domain is split into <code>STRIPES</code> stripes each guarded by its
 * lock.
 * <p>
 * At any point the stats may be lagging by X ( = INCONSISTENCY_WINDOW) data
 * points. A window of zero means the stripes are merged on every read.
 * <p>
 * Increasing X increases performance at the cost of accuracy (staleness).
 * <p>
 * Useful if the data source emits fairly random samples as to cause even
 * distribution amongst the stripes.
 * 
 * @author bgarg
 */
public class StripedLockedStatistics implements Statistics
{
    /**
     * At any point the stats may be lagging by these many data points. A window
     * of zero means the stripes are merged on every read.
     */
    public static int INCONSISTENCY_WINDOW = 1000;

    public final int stripeCount;

    /**
     * The global snapshot of the statistics. All reads are performed off this
     * instance.
     */
    private final MergeableStatistics global = new SynchronizedStatistics.Mergeable(
        new SimpleMergeableStatistics());

    /**
     * Local stats that are reset after being merged into the global snapshot of
     * the statistics object. Each of these is guarded by a separate lock.
     */
    private final ReusableStatistics[] stripes;

    /**
     * How many writes have happened since last sync to global stats.
     */
    private final AtomicInteger dirtyCount = new AtomicInteger(0);

    public StripedLockedStatistics(int s)
    {
        this.stripeCount = s;
        this.stripes = new ReusableStatistics[stripeCount];
        for (int i = 0; i < stripeCount; i++) {
            stripes[i] = new SimpleReusableStatistics();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void event(int value)
    {
        ReusableStatistics stat = stripes[value % stripeCount];
        synchronized (stat) {
            stat.event(value);
        }
        dirtyCount.incrementAndGet();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns <code>Integer.MAX_VALUE</code> in case no values have been added.
     */
    @Override
    public int minimum()
    {
        syncStripes();
        return global.minimum();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns <code>Integer.MIN_VALUE</code> in case no values have been added.
     */
    @Override
    public int maximum()
    {
        syncStripes();
        return global.maximum();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns <code>Float.NaN</code> in case no values have been added.
     */
    @Override
    public float mean()
    {
        syncStripes();
        return global.mean();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns <code>Float.NaN</code> in case no values have been added.
     */
    @Override
    public float variance()
    {
        syncStripes();
        return global.variance();
    }

    private void syncStripes()
    {
        if (setIfGreater(dirtyCount, INCONSISTENCY_WINDOW, 0)) {
            for (int i = 0; i < stripeCount; i++) {
                ReusableStatistics s = stripes[i];
                synchronized (s) {
                    global.merge(s);
                    s.reset();
                }
            }
        }
    }

    /**
     * The atomic
     * 
     * <pre>
     * if (ai.intValue() > comp) { 
     *     ai.setVaue(newValue) }
     * </pre>
     * 
     * Returns true if set was successful
     */
    private static boolean setIfGreater(AtomicInteger a, int comp, int newValue)
    {
        while (true) {
            int old = a.intValue();
            if (old > comp) {
                if (a.compareAndSet(old, newValue)) {
                    return true;
                }
                // Hit a race, retry
            }
            else {
                return false;
            }
        }
    }
}