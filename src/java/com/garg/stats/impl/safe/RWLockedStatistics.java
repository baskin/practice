package com.garg.stats.impl.safe;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.garg.stats.Statistics;

/**
 * A thread safe implementation of <code>Statistics</code>. Computation of all
 * statistics happens in the event producer thread guarded by a write lock.
 * Get'ting the statistics is just returning the computed values backed by a
 * read lock. Note that this uses ReadWriteLock which is susceptible to 
 * writer starvation in case of high read access.
 * <p>
 * Note:
 * Differs a little from the others in behavior of edge conditions.
 * Throws IllegalStateException in case of boundary violations.
 * <p>
 */
public class RWLockedStatistics implements Statistics {

	private int count = 0;

	private int min = Integer.MAX_VALUE;
	private int max = Integer.MIN_VALUE;

	private float mean = 0;
	private float varianceFactor = 0;

	private final ReadWriteLock lock = new ReentrantReadWriteLock();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void event(int value) {
		lock.writeLock().lock();
		try {
			if (count == Integer.MAX_VALUE) {
				throw new IllegalStateException("Capacity exceeded!");
			}
			
			// Increment the count of data points
			count++;

			// Update min and max
			if (value < min) {
				min = value;
			} else if (value > max) {
				max = value;
			}

			float delta = value - mean;
			// We can easily deduce that mean from the previous value
			// mean = oldMean + (value-oldMean)/count
			mean += delta / count;

			// We can easily deduce the variance from the previous value
			varianceFactor += delta * (value - mean);
		} finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float mean() {
		lock.readLock().lock();
		try {
			ensureAtLeast(1);
			return mean;
		} finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int minimum() {
		lock.readLock().lock();
		try {
			ensureAtLeast(1);
			return min;
		} finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int maximum() {
		lock.readLock().lock();
		try {
			ensureAtLeast(1);
			return max;
		} finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float variance() {
		lock.readLock().lock();
		try {
			ensureAtLeast(2);
			return varianceFactor / (count - 1);
		} finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * Ensure at least <code>min</code> data points.
	 */
	private void ensureAtLeast(int min) {
		if (count < min) {
			throw new IllegalStateException("At least " + min
				+ " data point(s) required");
		}
	}
}