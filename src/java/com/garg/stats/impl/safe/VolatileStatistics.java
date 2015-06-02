package com.garg.stats.impl.safe;

import com.garg.stats.Statistics;

/**
 * An implementation of <code>Statistics</code> which publishes stats by using
 * volatiles. Computation of all statistics happens in the event producer thread
 * guarded by the object monitor. Get'ting the statistics is lock free and the
 * readers do not content for fetching the statistics.
 * <p>
 * However, note that an event may be partially applied in the stats object
 * though the stats can be off by a max of one data point. So it is not thread
 * safe in the true sense.
 * <p>
 * Provides strongly consistent stats which fan out well under low write
 * contention.
 */
public class VolatileStatistics implements Statistics
{

    protected volatile int count = 0;

    protected volatile int min = Integer.MAX_VALUE;
    protected volatile int max = Integer.MIN_VALUE;

    protected volatile float mean = Float.NaN;
    private float varianceFactor = 0;
    protected volatile float variance = Float.NaN;

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void event(int value)
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