package com.garg.prep.cormen;

import com.garg.prep.Util;

public class OrderStatistics
{
	/**
	 * Find min ret[0] and max ret[1] of the given array.
	 * Dont compare each element to the minimum and maximum separately.
	 * Process elements in pairs. Compare the elements of a pair to each other.
	 * Then compare the larger element to the maximum so far, and compare the
	 * smaller element to the minimum so far.
	 * This leads to only 3 comparisons for every 2 elements.
	 */
	public static int[] minMax(int []a)
	{
		if (a == null || a.length  == 0) {
			return new int[]{};
		}
		int min = a[0], max = a[0];
		int start = 1;
		if ((a.length & 1) == 0) {
			max = a[1];
			if (a[0] > a[1]) {
				min = a[1];
				min = a[0];
			}
			start = 2;
		}
		for (int i = start; i < a.length;i+=2) {
			int smaller = a[i], larger = a[i+1];
			if (a[i] > a[i+1]) {
				smaller = a[i+1];
				larger = a[i];
			}
			if (smaller < min) {
				min = smaller;
			}
			if (larger > max) {
				max = larger;
			}
		}
		return new int[]{min, max};
	}
	
	public static void main(String[] args)
	{
		int[] a = {2, 3, 8, 6, 1};
		Util.print(minMax(a));
		int[] b = {2, 3, 8, 6, 1, -45};
		Util.print(minMax(b));
		int[] c = {};
		Util.print(minMax(c));
		int[] d = {2};
		Util.print(minMax(d));
		int[] e = {2, 2, 5, 5, -1, -1};
		Util.print(minMax(e));
	}
}
