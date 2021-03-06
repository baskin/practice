
package com.garg.scratchpad;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;

public class ContinuousRange
{
	public static void main(String[] args)
	{
	    int a[] = {1,2,3,4,5,7,8,45,46,100};
	    System.out.println(continuousRange(a));
	}

	// 1,2,3,4,5,7,8,45,46 will return [1,5] [7,8] [45,46]
	private static List<Pair<Integer, Integer>> continuousRange(int[] a)
	{
	    if (a == null || a.length == 0) {
	        return null;
	    }
	    List<Pair<Integer, Integer>> result = new ArrayList<Pair<Integer,Integer>>();
	    int beginIndex = 0, endIndex = 0;
	    for (int i = 1; i < a.length; i++) {
	        if (a[i] == a[i-1] + 1 || a[i] == a[i-1]) {
	            endIndex = i;
	        }
	        else if (a[i] > a[i-1] + 1) {
	            result.add(new Pair(a[beginIndex], a[endIndex]));
	            beginIndex = i;
	            endIndex = i;
	        }
	        else {
	            throw new IllegalArgumentException("Given array is not sorted in an asc fashion");
	        }
	    }
	    result.add(new Pair(a[beginIndex], a[endIndex]));
        return result;
	}
}
