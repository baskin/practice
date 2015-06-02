/*
 * Test.java
 *
 * $HeadURL: https://bhupi-practice.googlecode.com/svn/trunk/src/java/com/garg/test/Test.java $
 */

/*
 * Copyright (c) 2010 D. E. Shaw & Co., L.P. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of D. E. Shaw & Co., L.P. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with D. E. Shaw & Co., L.P.
 */

package com.garg.prep;

public class Arrays
{
    /** 
     * Recursive solution.
     * Also see http://shashank7s.blogspot.com/2011/04/wap-to-print-2d-array-matrix-in-spiral.html 
     */
    public static void printSpiral(int[][] a)
    {
        // tl = top left, br = bottom right.
        int tlx = 0, tly = 0;
        int brx = a[0].length - 1, bry = a.length - 1;
        
        while (tlx < brx && tly < bry) {
            printTopRight(a, tlx, tly, brx, bry);
            tly++; brx--;
            printBottomLeft(a, brx, bry, tlx, tly);
            tlx++; bry--;
        }
    }
    
    /**
     * Print a[x1..x2][y1] a[x2][y1+1..y2]
     * x1, y1: top left.
     * x2, y2: bottom right.
     * Both points are inclusive.
     */
    private static void printTopRight(int[][] a, int x1, int y1, int x2, int y2)
    {
        for (int i = x1; i <= x2; i++) {
            System.out.print(a[y1][i] + " ");
        }
        for (int i = y1+1; i <= y2; i++) {
            System.out.print(a[i][x2] + " ");
        }
    }
    
    /**
     * Print a[x1..x2][y1] a[x2][y1+1..y2]
     * x1, y1: bottom right.
     * x2, y2: top left.
     * Both points are inclusive.
     */
    private static void printBottomLeft(int[][] a, int x1, int y1, int x2, int y2)
    {
        for (int i = x1; i >= x2; i--) {
            System.out.print(a[y1][i] + " ");
        }
        for (int i = y1-1; i >= y2; i--) {
            System.out.print(a[i][x2] + " ");
        }
    }
    
	/**
	 * Write a function that computes the intersection of two arrays. The arrays
	 * are sorted. Then, what if one array is really larger than the other
	 * array?
	 */
	public static void printIntersectionOfSortedArrays(int[] a, int[] b)
	{
		int i = 0, j = 0;
		while (i < a.length && j < b.length) {
			if (a[i] == b[j]) {
				System.out.println(a[i] + " ");
				i++;
				j++; // INC BOTH
			}
			else if (a[i] < b[j]) {
				i++;
			}
			else {
				j++;
			}
		}
	}

    /**
     * An Array of integers is given, both +ve and -ve. You need to find the two
     * elements such that their sum is closest to zero
     */
	public static void twoElementSumClosestToZero(int[] a)
	{
	    // sort array
	    // Start i from left end and j from right end.
	    // s = a[i] + a[j]
	    // if s is too -ve, i++
	    // if s is too ve,  j--
	    // keep track of abs min.
	    // break when i >= j
	}

    /**
     * Given an integer 'k' and an sorted array A (can consist of both +ve/-ve
     * nos), output 2 integers from A such that a-b=k. PS: nlogn solution would
     * be to check for the occurence of k-a[i] (using binary search) when you
     * encounter a[i]. methods like hash consume space.
     */
	public static void twoElementsDiffEqualsk(int[] a)
	{
        // We could check for adjacent elements instead of checking for each
        // element. Start with the first two elements. In case the difference is
        // > k increment the first pointer. If the difference is < k increment
        // second pointer. Also when the two pointers become equal, increment
        // the second pointer to take the next adjacent number. Return when
        // difference becomes equal to k.
	    // It could be the case that k < 0. So, we should use abs(k) for comparison.
	}
	
	/**
	 * Finding three elements in an array whose sum is closest to an given number
	 * http://stackoverflow.com/questions/2070359/finding-three-elements-in-an-array-whose-sum-is-closest-to-an-given-number
	 */
	public static void threeElementsSumEqualsK(int[] a)
	{
        // Clearly, if we simply test all possible 3-tuples, we'd solve the
        // problem in O(n3) -- that's the brute-force baseline. Is it possible
        // to do better? What if we pick the tuples in a somewhat smarter way?
        // First, we invest some time to sort the array, which costs us an
        // initial penalty of O(n log n). Now we execute this algorithm:
        //
	    // for (i in 1..n-2) {
	    //   j = i  // Start where i is.
	    //  k = n  // Start at the end of the array.
        //
	    //  while (k >= j) {
	    //    // We got a match! All done.
	    //    return (A[i], A[j], A[k]) if (A[i] + A[j] + A[k] == 0)
        //
	    //    // We didn't match. Let's try to get a little closer:
	    //    //   If the sum was too big, decrement k.
	    //    //   If the sum was too small, increment j.
	    //    (A[i] + A[j] + A[k] > 0) ? k-- : j++
	    //  }
	    //  // When the while-loop finishes, j and k have passed each other and there's
	    //  // no more useful combinations that we can try with this i.
	    //} 
	    //
        // this algorithm allows the selection of the same element multiple
        // times. That is, (-1, -1, 2) would be a valid solution, as would (0,
        // 0, 0). 
	}
	
	/**
	 * Given a list of integers, print out all pairs of numbers that add to x.
	 * Again, analyze the run time.
	 */
	public static void main(String[] args) throws InterruptedException
	{
		int[] a = { 2, 4, 5, 12, 25, 67, 71, 71, 71, 80, 81, 84 };
		int[] b = { 3, 5, 71, 71, 71, 71, 71, 84 };
		printIntersectionOfSortedArrays(a, b);
		
        int array[][] = 
            { 
                {  1,  2,  3,  4 }, 
                {  5,  6,  7,  8 }, 
                {  9, 10, 11, 12 },
                { 13, 14, 15, 16 },
                { 17, 18, 19, 20 }
            };
        printSpiral(array);
	}
}
