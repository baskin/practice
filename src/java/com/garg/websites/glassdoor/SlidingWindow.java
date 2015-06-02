package com.garg.websites.glassdoor;

import com.garg.concepts.ds.trees.Heap;

public class SlidingWindow
{
	private static class SpecialHeap extends Heap
	{
		public SpecialHeap(int[] b)
		{
			super(b, false);
		}

		/**
		 * Replace one occurrence of oldVal with new val.
		 * 
		 * k -> heap size.
		 * complexity:
		 * loop: O(k),    ensureHeap: k * log(k)
		 * total: O(k) + k*log(k)
		 */
		public boolean replace(int newVal, int oldVal)
		{
			if (oldVal == newVal) {
				return true;
			}
			for (int i = 1; i <= myLastIndex; i++) {
				if (myHeap[i] == oldVal) {
					myHeap[i] = newVal;
					if (myIsMax  && newVal < oldVal ||
						!myIsMax && newVal > oldVal) 
					{
						super.ensureSubHeap(i, true);
					}
					else {
						for (int j = i/2; j >=1; j/=2) {
							super.ensureSubHeap(j, false);
						}
					}
					// Aliter: Instead of above 2 steps perform heapify.
					// super.heapify();
					return true;
				}
			}
			return false;
		}
		
	}
	
	/**
	 * Giving a windows size K and an array of size N, find the minimum of 
	 * each window as it slides through the array
	 * 
	 *  O(nk)
	 */
	public static void slidingWindowMin(int[] a, int k)
	{
		int minIndex = 0;
		for (int j = 0; j < k; j++) {
			if (a[j] < a[minIndex]) {
				minIndex = j;
			}
		}
		System.out.print(a[minIndex] + " ");
		for (int i = 1; i < a.length - k + 1; i++) {
			if (i-1 == minIndex) {
				int min = Integer.MAX_VALUE;
				for (int j = i; j < i + k; j++) {
					if (a[j] < min) {
						minIndex = j;
						min = a[j];
					}
				}
			}
			else if (a[i+k-1] < a[minIndex]) {
				minIndex = i+k-1;
				
			}
			System.out.print(a[minIndex] + " ");
		}
	}

	// complexity:
	// O(nk)
	public static void slidingWindowMinUsingHeap(int[] a, int k)
	{
		int[] b = new int[k];
		for (int i = 0; i < k; i++) {
			b[i] = a[i];
		}	// {2, 5, 1, 7, -3, 11, 4};
		SpecialHeap heap = new SpecialHeap(b);
		System.out.print(heap.peek() + " ");
		for (int i = k; i < a.length; i++) {
			heap.replace(a[i], a[i-k]);
			System.out.print(heap.peek() + " ");
		}
	}
	
	public static void main(String[] args)
	{
		int[] a = new int[] {2, 5, 1, 7, -3, 11, 4};
		slidingWindowMin(a, 2);
		System.out.println();
		slidingWindowMinUsingHeap(a, 2);
	}
}
