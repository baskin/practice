package com.garg.projects.stats;

/**
 * Statistics that can be reused after it has been merged with a 
 * <code>MergeableStatistics</code>.
 */
public interface ReusableStatistics extends Statistics {
	
	/**
	 * Returns the count of data points in the statistics.
	 */
	int count();
	
	/**
	 * Prepare for reuse. To be used once the stat has been merged onto the global
	 * snapshot.
	 */
	void reset();
}
