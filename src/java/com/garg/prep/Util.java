package com.garg.prep;

import java.util.Comparator;

public class Util 
{	
	/** Print the int array. */
	public static void print(int[] a)
	{
		StringBuilder sb = new StringBuilder();
		for (int e : a) {
			sb.append(e);
			sb.append(" ");
		}
		System.out.println(sb.toString());
	}
	
	/** Swap ith element in a with jth element. */
	public static void swap(int[] a, int i, int j) 
	{
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	/** Swap ith element in a with jth element. */
    public static <T> void swap(T[] a, int i, int j) 
    {
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
	
	// XXXX doesn't work!
	private static void swap(Object i, Object j)
	{
		Object temp = i;
		i = j;
		j = temp;
		// i.setValue() would have worked.
	}
	
	/********************* PIVOTING *********************************/ 
	
	public static int pivot(int[] a, int l, int h)
	{
		return pivot(a, l, h, null);
	}
	
	/**
	 * Partition the array into 3 regions defined by a pivot index p=h, 
	 * such that
	 * <pre>
	 *    a[l:p-1] <= a[p] and
	 *    a[p+1:h] >  a[p].
	 * </pre>
	 * @param a the array.
	 * @param l the lower index of the array, inclusive.
	 * @param h the higher index of the array, inclusive.
	 * @return the pivot index.
	 */
	public static int pivot(int[] a, int l, int h, Comparator<Integer> cmp) 
	{ 
		if (cmp == null) {
			cmp = new Comparator<Integer>() {
				@Override
				public int compare(Integer o1, Integer o2) {
					return o1.compareTo(o2);
				}
			};
		}
		
		//
		//   pivot = a[h]  
		//   iterator j from l to  h - 1.
		//   let i be such that a[k] <= pivot, for all k < i.
		//   (note that a[i] itself is >= pivot)
		//   
		// 
		//   | |--------| |-------------| |-------------| |
		//   |l|        |i|             |j|             |h|
  		//   | |--------| |-------------| |-------------| |
		//   |<-(<=p)-->|
		//              |<---(>p)------>|
		//
		// Check loop-invariant is true
		// 1> initialization 
		// 2> during iteration 
		// 3> termination.
		//
		int i = l; // see initial conditions.
		for (int j = l; j < h; j++) {
			if (cmp.compare(a[j], a[h]) <= 0) {// if (a[j] <= a[h])  
				swap(a, i, j);
				i++;
			}
		}
		swap(a, i, h);
		return i;
	}
	
	/**
	 * A randomized version of {@link #pivot(int[], int, int)} which chooses
	 * the pivot randomly.
	 */
	public static int randomPivot(int[] a, int l, int h, Comparator<Integer> cmp) 
	{
		int randomIndex = (int)(Math.random() * ( h - l + 1)) + l;
		swap(a, randomIndex, h);
		return pivot(a, l, h, cmp);
	}
	
	/**
	 * A version of {@link #pivot(int[], int, int)} which chooses the pivot
	 * to the the median of l, (l+h/2), h.
	 */
	public static int medianOf3Pivot(int[] a, int l, int h) 
	{
		int m = (l + h)/2;
		// First let the smaller of a[l], a[h] be at index 'small'.
		int small = l, big = h;
		if (a[h] < a[l]) {
			small = h;
			big = l;
		}
		int median = m;
		if (a[m] < a[small]) {
			median = small;
		}
		else if (a[m] > a[big]) {
			median = big;
		}
		swap(a, median, h);
		return pivot(a, l, h);
	}
	
	/**
	 * Merge the sorted sub-array a[l:m] with the sorted sub-array a[m+1:h],
	 * Cormen's single loop merge.
	 */
	public static void merge(int[]a, int l, int m, int h)
	{
		// Scheme:
		// put a[l:m]   in L[0:m-l],   put inf in L[m-l+1]
		// put a[m+1:h] in R[0:h-m-1], put inf in R[h-m]
		int[] L = new int[m-l+2];
		int[] R = new int[h-m+1];
		for (int i = l; i <= m; i++) {
			L[i-l] = a[i];
		}
		L[m-l+1] = Integer.MAX_VALUE;
		for (int i = m+1; i <= h; i++) {
			R[i-m-1] = a[i];
		}
		R[h-m] = Integer.MAX_VALUE;
		
		int j = 0, k = 0;
		for (int i = l; i <= h; i++) {
			if (L[j] <= R[k]) {
				a[i] = L[j];
				j++;
			}
			else {
				a[i] = R[k];
				k++;
			}
		}
	}
	
	/**
	 * Merge the sorted sub-array a[l:m] with the sorted sub-array a[m+1:h],
	 * using temp as a temporary array.
	 */
	public static void merge(int[]a, int[] temp, int l, int m, int h)
	{
		if (temp.length <  h - l + 1) {
			throw new IllegalArgumentException();
		}
		
		// i1 cursor over a[l:m]
		// i2 cursor over a[m+1:h]
		int i1 = l, i2 = m + 1;
		int j = l;
		while (i1 <= m && i2 <= h) {
			if (a[i1] < a[i2]) {
				temp[j++] = a[i1++]; 
			}
			else {
				temp[j++] = a[i2++];
			}
		}
		// Copy remaining.
		while (i1 <= m) {
			temp[j++] = a[i1++];
		}
		while (i2 <= h) {
			temp[j++] = a[i2++];
		}
		
		// Copy temp[] to a[]
		j = l;
		for (int i = l; i <= h; i++, j++) {
			a[i] = temp[j];
		}
	}
	
	/**
	 * Compute and return the rank array rank, such that
	 * rank[i] gives the index of a[i] in a sorted version of the array.
	 * c[rank[i]] = a[i], will yield c[] --> a sorted a[].
	 * 
	 * @param a the int array.
	 * @return the rank array
	 */
	public static int[] rank(int[] a)
	{
		int[] rank = new int[a.length];
		for (int start = 0; start < a.length - 1; start++) {
			for (int j = start + 1; j < a.length; j++) {
				if (a[start] > a[j]) {
					rank[start]++;
				}
				else {
					rank[j]++;
				}
			}
		}
		return rank;
	}
	
	/** Return Kth largest in a[]. */
	public static int kthLargest(int[] a, int k)
	{
		return iterativeKthLargest(a, 0, a.length - 1, k);
	}

	/** Return Kth largest in a[l:h]. */
	private static int kthLargest(int[] a, int l, int h, int k)
	{
		int pivot = pivot(a, l, h);
		if (k == pivot) {
			return a[k];
		}
		if (k > pivot) {
			return kthLargest(a, pivot + 1, h, k - pivot);
		}
		else {
			return kthLargest(a, l, pivot - 1, k);
		}
	}
	
	/** Iterative version of {@link #kthLargest(int[], int, int, int)}. */
	private static int iterativeKthLargest(int[] a, int l, int h, int k)
	{
		if (k < l || k > h) {
			throw new IllegalArgumentException();
		}
		
		int pivot = -1;
		while (k != pivot) {
			pivot = pivot(a, l, h);
			if (k > pivot) {
				k = k - pivot;
				l = pivot + 1;
			}
			else if (k < pivot){
				h = pivot - 1;
			}
		}
		return a[k];
	}

	public static void main(String[] args) 
	{
		int[] a = new int[] {5, 6, 9, 1, 0, -34, 99, 99, 345, 1, 0, 3, 1, 1, 1};
		
		for (int i = 0; i < a.length; i++) {
			System.out.print(
				kthLargest(a, i) + " ");
		}
		System.out.println();
		int b[] = new int[] {1, 4, 9, 23,    2, 3, 11, 30, 45, Integer.MAX_VALUE};
		// following array will cause AIOBE.
		// int b[] = new int[] {1, 4, 9, 23,    2, 3, 11, 30, 45, Integer.MAX_VALUE};
		merge(b, 0, 3, b.length - 1);
		Util.print(b);
	}
}
