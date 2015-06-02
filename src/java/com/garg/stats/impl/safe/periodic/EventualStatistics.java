package com.garg.stats.impl.safe.periodic;

import com.garg.stats.MergeableStatistics;
import com.garg.stats.ReusableStatistics;
import com.garg.stats.Statistics;
import com.garg.stats.impl.SimpleMergeableStatistics;
import com.garg.stats.impl.SimpleReusableStatistics;
import com.garg.stats.impl.safe.SynchronizedStatistics;

/**
 * A thread safe implementation of <code>Statistics</code> that reduces write
 * contention by publishing stats periodically instead of sync'ing them on each
 * update. The result is statistics that are delayed.
 * <p>
 * At any point there will be an inconsistency window of at max X ( =
 * <code>INCONSISTENCY_ACCESS_COUNT</code>) writes per thread. This means that
 * at any moment the stats may be lagging by a maximum of X data points per
 * thread. Increasing X increases performance at the cost of accuracy.
 * <p>
 * Besides tuning the inconsistency window, you can override the method
 * {@link #shouldSync(AccessState)} to write you custom sync policy.
 * <p>
 * TODOO better name!
 * 
 * @author bgarg
 */
public class EventualStatistics implements Statistics
{
    /**
     * At any point the stats may be lagging by these many data points in each
     * thread.
     */
    public static int INCONSISTENCY_WINDOW = 1000;

    /**
     * Local stats that are reset after being merged into the global snapshot of
     * the statistics object.
     */
    private final ThreadLocal<ReusableStatistics> local =
        new ThreadLocal<ReusableStatistics>() {
            @Override
            public ReusableStatistics initialValue()
            {
                return new SimpleReusableStatistics();
            }
        };

    /**
     * The global snapshot of the statistics. All reads are performed off this
     * instance.
     */
    private final MergeableStatistics global = 
        new SynchronizedStatistics.Mergeable(new SimpleMergeableStatistics());

    /**
     * A data structure to hold access information about the local and global
     * instances of statistics. This aids in making decision on when to sync
     * the global state. 
     */
    private final ThreadLocal<AccessState> localAccessState =
        new ThreadLocal<AccessState>() {
            @Override
            public AccessState initialValue()
            {
                return new AccessState();
            };
        };

    private static class AccessState
    {
        // dirty count.
        private int writeCount = 0;
        private int readCount = 0;

        int writeCount()
        {
            return writeCount;
        }

        int readCount()
        {
            return readCount;
        }

        boolean isDirty()
        {
            return writeCount > 0;
        }

        void recordRead()
        {
            readCount++;
        }

        void recordWrite()
        {
            writeCount++;
        }

        void recordSync()
        {
            writeCount = 0;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void event(int value)
    {
        local.get().event(value);
        recordWrite();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns <code>Integer.MAX_VALUE</code> in case no values have been added.
     */
    @Override
    public int minimum()
    {
        recordRead();
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
        recordRead();
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
        recordRead();
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
        recordRead();
        return global.variance();
    }

    private void recordRead()
    {
        localAccessState.get().recordRead();
        trySync();
    }

    private void recordWrite()
    {
        localAccessState.get().recordWrite();
        trySync();
    }

    /**
     * Sync the global state with the thread local state, if required.
     */
    private void trySync()
    {
        AccessState state = localAccessState.get();
        if (state.isDirty()) {
            if (shouldSync()) {
                sync();
            }
        }
    }

    /**
     * Subclasses can override to tune the sync policy.
     */
    protected boolean shouldSync() {
        return (localAccessState.get().writeCount() > INCONSISTENCY_WINDOW);
    }

    private void sync()
    {
        global.merge(local.get());
        // Prepare the local instance for reuse.
        local.get().reset();
        localAccessState.get().recordSync();
    }
}