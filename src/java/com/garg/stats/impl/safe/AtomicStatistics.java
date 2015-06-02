package com.garg.stats.impl.safe;

import java.util.concurrent.atomic.AtomicReference;

import com.garg.stats.Statistics;
import com.garg.stats.impl.SimpleStatistics;

/**
 * A Thread safe implementation that uses java.util.concurrent.atomic.* constructs.
 * <p>
 * More garbagy than other implementations but performs well in general.
 * <p>
 */
public class AtomicStatistics implements Statistics
{
    private final AtomicReference<SimpleStatistics> stats =
        new AtomicReference<SimpleStatistics>(new SimpleStatistics());

    /**
     * {@inheritDoc}
     */
    @Override
    public void event(int value)
    {
        while (true) {
            SimpleStatistics prev = stats.get();
            // TODO use an object pool (one that avoids frequent memory barriers).
            SimpleStatistics next = new SimpleStatistics(prev);
            next.event(value);
            if (stats.compareAndSet(prev, next)) {
                return;
            }
            // Data race. Retry.
            // TODO add jitter instead of busy spinning?
            // TODO If we can live with events being partially applied,
            // we can granularize the atomic references to reduce the
            // contention even further.
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int minimum()
    {
        return stats.get().minimum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int maximum()
    {
        return stats.get().maximum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float mean()
    {
        return stats.get().mean();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float variance()
    {
        return stats.get().variance();
    }
}