package com.garg.websites.greplin;

/**
 * http://challenge.greplin.com/
 */
public class Challenge3 {
	/**
	 * For the final task, you must find all subsets of an array
	 * where the largest number is the sum of the remaining numbers.
	 * For example, for an input of:
	 * <pre>
	 * (1, 2, 3, 4, 6)
	 * 
	 * the subsets would be
	 * 
	 * 1 + 2 = 3
	 * 1 + 3 = 4
	 * 2 + 4 = 6
	 * 1 + 2 + 3 = 6
	 * </pre>
	 */
	public static int subsetCount(int[] a)
	{
		int count = 0;
		// assume sorted array.
		for (int i = 0; i < 1 << a.length; i++) {
			boolean startSum = false;
			int sum = 0, max = Integer.MIN_VALUE;
			for (int j = 0; j < a.length; j++) {
				if ((i & (1 << j)) != 0) {
					if (startSum) {
						sum += a[a.length-j-1];
					}
					else {
						// Since we assume array is sorted,
						// the last element will be max. 
						max = a[a.length-j-1];
						startSum = true;
					}
				}
			}
			if (sum == max) {
				count++;
			}
		}
		return count;
	}

	public static void main(String[] args) {
		int[] a = { 3, 4, 9, 14, 15, 19, 28, 37, 47, 50, 54, 56, 59, 61, 70,
				73, 78, 81, 92, 95, 97, 99 };
		System.out.println(subsetCount(a));
	}
}
