package com.garg.interview.amazon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Amazon 
{
	
	/**
	 * Find the minimal number of removals from array a such that the max is less than
	 * twice the min.
	 */
	public static final int minRemovals(int []a)
	{
		Arrays.sort(a);
		int minCount = -1;
		for (int i = 0; i < a.length - 1; i++) {
			// Arrays.binarySearch() returns the following:
			// index of the search key, if it is contained in the array; otherwise,
			// (-(insertion point) - 1). The insertion point is defined as the point
			// at which the key would be inserted into the array: the index of the
			// first element greater than the key, or a.length if all elements in
			// the array are less than the specified key.
			int e = 2 * a[i] + 1;
			// If no such subarray exists (e.g. 1, 100), we'll return -1.
			int index = Arrays.binarySearch(a, i, a.length - 1, e);
			if (index > 0) {
				// Element found
				int min = (i) + (a.length - 1 - index);
				if (min < minCount) {
					minCount = min;
				}
			}
			else {
				// Element not found. Insert index.
				index = -(index + 1);
				if (index > i+1);
			}
			
		}
		
		return -1;
	}

	/**
	 * ------------------------------------
	 * -----------j------------i-----------
	 * 
	 * j is point of next insert
	 * i iterates over the array, skipping past chars from tokens
	 * 
	 * a[k], k < j =>  is all wanted array, with j storing final boundary of array
	 * a[k], j < k < i => all unwanted elements, can be replaced
	 * a[k], k > i => unprocessed array
	 * 
	 */
	public static String removeTokens(final char[] s, final char[] tokens)
	{
		int j = 0;
		for (int i = 0; i < s.length; i++) {
			if (!matches(s[i], tokens)) {
				s[j] = s[i];
				j++;
			}
		}
		return String.valueOf(s).substring(0, j);
	}
	
	private static boolean matches(final char c, final char[] tokens) 
	{
		for (char t : tokens) {
			if (t == c) return true;
		}
		return false;
	}

	/**
	 * Remove 'b' and 'ac'.
	 * 
	 * ------------------------------------
	 * -----------j------------i-----------
	 * 
	 * j is point of next insert
	 * i iterates over the array, skipping past chars from tokens
	 * 
	 * a[k], k < j =>  is all wanted array, with j storing final boundary of array
	 * a[k], j < k < i => all unwanted elements, can be replaced
	 * a[k], k > i => unprocessed array
	 * 
	 */
	public static String removeStrings(final char[] s)
	{
		int j = 0;
		// state = 1, normal state
		// state = 2, prev value was 'a', to identify 'ac'
		int state = 1;
		for (int i = 0; i < s.length; i++) {
			
			// 'recursively remove 'ac''
			if (j > 1 && s[j-2] == 'a' && s[j-1] == 'c') {
				j -= 2;
			}
			
			if (state == 2) {
				state = 1;
				if (s[i] != 'c') {
					s[j] = 'a';
					j++;
				}
				else {
					continue;
				}
			}
			
			if (s[i] == 'b') {
				
			}
			else if (s[i] == 'a') {
				state = 2;
			}
			else {
				s[j] = s[i];
				j++;
			}
		}
		return String.valueOf(s).substring(0, j);
	}
	
	
    /**
     * sorted array. return range to continuous stream of numbers. e.g.
     * 1,2,3,4,5,7,8,45,46 will return [1,5] [7,8][45,46]
     */
	public static List<int[]> continuousRange(final int[] a)
	{
	    if (null == a || a.length == 0) {
	        return null;
	    }
	    if (a.length == 1) {
	        int [] range = new int[] {a[0], a[0]};
	        return Collections.singletonList(range);
	    }
	    
	    int s = a[0];
	    int prev = a[0];
	    
	    List<int[]> result = new ArrayList<int[]>();
	    
	    for (int i = 0; i < a.length; i++) {
	        if (i == a.length - 1) {
	            int [] range;
	            if (a[i] - prev > 1) {
	                range = new int[] {s, prev};
	                result.add(range);
	                range = new int[] {a[i], a[i]};
	                result.add(range);
	            }
	            else {
	                range = new int[] {s, a[i]};
	                result.add(range);
	            }
	        }
	        else if (a[i] - prev > 1) {
	            int [] range = new int[] {s, prev};
	            result.add(range);
	            s = a[i];
	        }
	        prev = a[i];
	    }
	    
	    return result;
	}

	public static void main(String[] args)
	{
	    
	    int [] a = new int[] {-5, 1,2,3,4,5,7,8,45,46, 47, 100, 101};
	    for (int[] r :  continuousRange(a)) {
	        System.out.println(r[0] + "," + r[1]);
	    }
	    
	    System.exit(0);
	      
	    
	    
		// minRemovals tests
//		int a1[] = {1, 3, 7, 11, 100};
//		System.out.println(minRemovals(a1));
//		int a2[] = {1, 2, 3, 7, 11, 100};
//		System.out.println(minRemovals(a2));
		
		
		String strs[] = {"acbac", "accddgf", "abcabcbbccsdsfdfdd", "bbbb", "bbbbbdbbbbb"};
//		char tokens[] = {'b', 'f'};
		
		for (String s : strs) {
//			System.out.println(removeTokens(s.toCharArray(), tokens));
			System.out.println(removeStrings(s.toCharArray()));
		}
	}
	
}
