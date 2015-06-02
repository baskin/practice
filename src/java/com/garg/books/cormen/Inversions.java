package com.garg.books.cormen;

public class Inversions
{
	public static void main(String[] args)
	{
		int[] a = {2, 3, 8, 6, 1};
		printInversions(a);
		System.out.println(countInversionsUsingMergeSort(a, 0, a.length - 1));
	}

	/**
	 * O(n*log(n))
	 */
	private static int countInversionsUsingMergeSort(int[] a, int l, int h)
	{
		if (l < h) {
			int m = (l+h)/2;
			return countInversionsUsingMergeSort(a, l,   m) + 
				   countInversionsUsingMergeSort(a, m+1, h) + 
				   mergeCount(a, l, m, h);
		}
		return 0;
	}

	/**
	 * Cormen's single loop merge.
	 */
	private static int mergeCount(int[] a, int l, int m, int h)
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

		int inversions = 0;
		int j = 0, k = 0;
		for (int i = l; i <= h; i++) {
			if (L[j] <= R[k]) {
	 			a[i] = L[j];
				j++;
			}
			else {
				a[i] = R[k];
				k++;
				// Whenever an element from R is included in the merged array,
				// the remaining elements in L would be > that element.
				inversions += (L.length - 1) // last element is a dummy sentinal (inf)
				               - j;
			}
		}
		return inversions;
	}



	// Simple O(n2) algo.
	private static void printInversions(int[] a)
	{
		for (int i = a.length - 1; i >= 0 ; i--) {
			for (int j = i - 1; j >= 0; j--) {
				if (a[i] < a[j]) {
					System.out.printf("{%d,%d}", a[j], a[i]);
				}
			}
		}
	}
}
