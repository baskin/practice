package com.garg.projects.stats;

/**
 * Statistics that can merged with one another. This interface can be used to achieve
 * some level of parallelization by splitting the stats collection across various threads
 * or across various locks and then merging them when needed to obtain the complete 
 * snapshot.
 */
public interface MergeableStatistics extends Statistics {
	
	/**
	 * Merge the given stats unto itself.
	 */
	void merge(ReusableStatistics stats);
}
