package com.garg.websites.wu;

/**
 * Find largest Sum/Product of contiguous elements in an array.
 */
public class LargestXInArray
{
	/**
	 * Dynamic programming O(n).
	 * 
	 * Let S(i): be largest sum of array ending @index i. We need S(n-1). While
	 * iterating over a[i], consider S(i-1). There are 2 cases: either S{i) has
	 * contribution from a[i-1] or it does not. If it does not we need another
	 * variable to keep track of largest sum of contiguous elements ending @
	 * i-1. bSum(i-1). Hence,
	 * 
	 * S(i) = max {bSum(i-1) + a[i], S(i-1)} bSum(i) = bSum(i-1) + a[i], if
	 * bSum(i-1) + a[i] > 0 0 , otherwise
	 * 
	 * Start by solving in a bottom-up fashion. i.e. solve for S(0), S(1), ...
	 * S(n-1).
	 */
	public static int largestSumInArray(int[] a)
	{
		int S = Integer.MIN_VALUE;
		int bSum = 0;
		for (int i = 0; i < a.length; i++) {
			S = Math.max(bSum + a[i], S);
			bSum = bSum + a[i] > 0 ? bSum + a[i] : 0;
			System.out.println(S + "   " + bSum);
		}
		return S;
	}

	/**
	 * Find the largest product of elements in a contiguous subarray A[i .. j] .
	 * A. We try and use dynamic programming.
	 */
	public static int largestProductInArrayDynamic(int[] a)
	{
		if (a.length < 2) {
			return a[0];
		}

		// Keep
		// product(i) : max product of subarray ending in i
		// bProductPos(i): max contiguous +ve product including a[i]
		// bProductNeg(i): max contiguous -ve product including a[i]
		int product = Integer.MIN_VALUE;
		int bProductPos = 0;
		int bProductNeg = 0;
		// this simulates NA (when a pos/neg product is not possible)
		// when this is true, the corresponding product would be 0.
		boolean noPosProduct = true;
		boolean noNegProduct = true;

		for (int i = 0; i < a.length; i++) {
			if (a[i] > 0) {
				// pos product
				if (noPosProduct) {
					bProductPos = a[i];
				}
				else {
					bProductPos *= a[i];
				}
				noPosProduct = false;
				// neg product
				if (noNegProduct) {
					noNegProduct = true;
				}
				else {
					noNegProduct = false;
					bProductNeg *= a[i];
				}
			}
			else if (a[i] < 0) {
				boolean noPosProductOld = noPosProduct;
				int bProductPosOld = bProductPos;

				// pos product
				if (noNegProduct) {
					noPosProduct = true;
				}
				else {
					noPosProduct = false;
					bProductPos = bProductNeg * a[i];
				}
				// neg product
				noNegProduct = false;
				if (noPosProductOld) {
					bProductNeg = a[i];
				}
				else {
					bProductNeg = bProductPosOld * a[i];
				}
			}
			else {
				bProductNeg = bProductPos = 0;
				noNegProduct = noPosProduct = true;
			}
			product = Math.max(product, bProductPos);
		}
		return product;
	}
	
	/**
	 * Find the largest product of elements in a contiguous subarray A[i .. j] .
	 * A. Well, if we are dealing with integers, the more you multiply the
	 * larger the product. The only problem is with 0's and negative products.
	 * So you need to examine separately every sequence of consecutive non-zero
	 * numbers. If the number of negatives is odd, you need to remove one. Try
	 * removing the leftmost -ve and then try the rightmost -ve. You should
	 * stumble upon the largest number.
	 */

	public static void main(String[] args)
	{
		int a[] = { -4, -2, -5, 7, 3, 0, 0, -3, -6, 6, 4, 9, 13, -3, 10, 0, -25 };
		System.out.println(largestSumInArray(a));
		System.out.println(largestProductInArrayDynamic(a));
	}
}
