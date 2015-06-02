package com.garg.projects.stats.impl;

import com.garg.projects.stats.MergeableStatistics;
import com.garg.projects.stats.ReusableStatistics;

/**
 * A bare bones implementation of <code>MergeableStatistics</code>.
 * The class is not thread safe.
 */
public class SimpleMergeableStatistics extends SimpleStatistics implements
    MergeableStatistics
{
    /**
     * {@inheritDoc}
     */
    @Override
    public void merge(final ReusableStatistics stats)
    {
        if (Integer.MAX_VALUE - count < stats.count()) {
            throw new RuntimeException("Capacity exceeded!");
        }

        // Increment the count of data points
        count += stats.count();

        // Update min and max
        if (stats.minimum() < min) {
            min = stats.minimum();
        }
        if (stats.maximum() > max) {
            max = stats.maximum();
        }

        if (!Float.isNaN(stats.mean())) {
            if (Float.isNaN(mean)) {
                mean = stats.mean();
            }
            else {
                mean =
                    (mean * count + stats.mean() * stats.count())
                        / (count + stats.count());
            }
        }
        if (!Float.isNaN(stats.variance())) {
            if (Float.isNaN(variance)) {
                variance = stats.variance();
            }
            else {
                // Update the variance.
                // TODO 
                // http://en.wikipedia.org/wiki/Algorithms_for_calculating_variance#Parallel_algorithm
            }
        }
    }

}