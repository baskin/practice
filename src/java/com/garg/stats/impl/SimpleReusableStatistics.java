package com.garg.stats.impl;

import com.garg.stats.ReusableStatistics;

/**
 * A bare bones implementation of <code>ReusableStatistics</code> that is not
 * thread safe.
 */
public class SimpleReusableStatistics extends SimpleStatistics implements
    ReusableStatistics
{
    /**
     * {@inheritDoc}
     */
    @Override
    public int count()
    {
        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset()
    {
        count = 0;

        min = Integer.MAX_VALUE;
        max = Integer.MIN_VALUE;

        mean = Float.NaN;
        varianceFactor = 0;
        variance = Float.NaN;
    }
}