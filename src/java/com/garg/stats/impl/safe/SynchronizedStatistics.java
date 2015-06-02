package com.garg.stats.impl.safe;

import com.garg.stats.MergeableStatistics;
import com.garg.stats.ReusableStatistics;
import com.garg.stats.Statistics;

/**
 * A thread safe implementation of <code>Statistics</code>. This one takes
 * another <code>Statistics</code> and decorates it into synchronized calls.
 */
public class SynchronizedStatistics implements Statistics
{
    private final Statistics stats;
    
    private final Object LOCK = new Object(); 

    /**
     * A thread safe implementation of <code>MergeableStatistics</code>. This
     * one takes another <code>MergeableStatistics</code> and decorates it into
     * synchronized calls.
     */
    public static class Mergeable extends SynchronizedStatistics implements
        MergeableStatistics
    {
        private final MergeableStatistics stats;

        public Mergeable(final MergeableStatistics s)
        {
            super(s);
            this.stats = s;
        }

        @Override
        public synchronized void merge(ReusableStatistics stats)
        {
            this.stats.merge(stats);
        }
    }

    public SynchronizedStatistics(final Statistics s)
    {
        stats = s;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void event(int value)
    {
        synchronized (LOCK) {
            stats.event(value);            
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns <code>Integer.MAX_VALUE</code> in case no values have been added.
     */
    @Override
    public int minimum()
    {
        synchronized (LOCK) {
            return stats.minimum();            
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns <code>Integer.MIN_VALUE</code> in case no values have been added.
     */
    @Override
    public int maximum()
    {
        synchronized (LOCK) {
            return stats.maximum();            
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns <code>Float.NaN</code> in case no values have been added.
     */
    @Override
    public float mean()
    {
        synchronized (LOCK) {
            return stats.mean();            
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns <code>Float.NaN</code> in case no values have been added.
     */
    @Override
    public float variance()
    {
        synchronized (LOCK) {
            return stats.variance();            
        }
    }
}