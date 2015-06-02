package com.garg.prep;

public class InPlaceArrayOperations
{
	public static void reverse(int[] a, int start, int end)
	{
		while (start < end) {
			int temp = a[start];
			a[start] = a[end];
			a[end] = temp;

			start++;
			end--;
		}
	}

	// i -> (i + k) mod sz 
	/**
	 * A = PQ (P, Q are subsegments), Find QP.
	 * r(A) = r(Q)r(P)
	 * 
	 * QP = r(r(QP)
	 *    = r(r(P)r(Q))
	 */
	public static void rotateCyclic(int[] a, int k)
	{
		// Left rotate
		reverse(a, 0, k - 1);
		reverse(a, k, a.length - 1);
		reverse(a, 0, a.length - 1);
	}

	/**
	 * array     : 1 2 3 4 5 6 7 8
	 * outshuffle: 1 5 2 6 3 7 4 8 (outer boundaries remain same)
	 * inshuffle : 5 1 6 2 7 3 8 4
	 */
	public static void inshuffle(int[] a)
	{
		
	}

	public static void main(String[] args)
	{
		int[] a = { 0, 1, 2, 3, 4, 5, 6, 7 };
		rotateCyclic(a, 2);
		Util.print(a);
	}
}
