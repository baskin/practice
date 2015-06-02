package com.garg.stats.impl;

import com.garg.stats.Statistics;

/**
 * A bare bones simple implementation of <code>Statistics</code>. Computation of
 * all statistics happens in {@link #event(int)} and all getters are thus O(1).
 * This class is not thread safe.
 * <p>
 * Assumptions:
 * <ol>
 * <li>Maximum of Integer.MAX_VALUE data points are supported.</li>
 * <li>The getters return a special sentinel value in case the stat is accessed
 * before adding any data point. See method docs for more details.</li>
 * </ol>
 * <p>
 */
public class SimpleStatistics implements Statistics
{
    protected int count = 0;

    protected int min = Integer.MAX_VALUE;
    protected int max = Integer.MIN_VALUE;

    protected float mean = Float.NaN;
    protected float varianceFactor = 0;
    protected float variance = Float.NaN;
    
    public SimpleStatistics()
    {
    }
    
    /**
     * Copy constructor.
     */
    public SimpleStatistics(final SimpleStatistics c)
    {
        this.count = c.count;
        this.max = c.max;
        this.min = c.min;
        this.mean = c.mean;
        this.variance = c.variance;
        this.varianceFactor = c.varianceFactor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void event(int value)
    {
        if (count == Integer.MAX_VALUE) {
            throw new RuntimeException("Capacity exceeded!");
        }

        // Increment the count of data points
        count++;

        // Update min and max
        if (value < min) {
            min = value;
        }
        else if (value > max) {
            max = value;
        }

        if (count == 1) {
            mean = value;
        }
        else {
            float delta = value - mean;
            // Deduce mean from the previous value
            // mean = oldMean + (value-oldMean)/count
            mean += delta / count;

            if (count > 1) {
                // Deduce the variance from the previous value
                varianceFactor += delta * (value - mean);
                variance = varianceFactor / (count - 1);
            }
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
        return min;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns <code>Integer.MIN_VALUE</code> in case no values have been added.
     */
    @Override
    public int maximum()
    {
        return max;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns <code>Float.NaN</code> in case no values have been added.
     */
    @Override
    public float mean()
    {
        return mean;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns <code>Float.NaN</code> in case no values have been added.
     */
    @Override
    public float variance()
    {
        return variance;
    }
}