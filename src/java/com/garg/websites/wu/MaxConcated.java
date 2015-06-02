package com.garg.websites.wu;

import java.util.Comparator;

import com.garg.concepts.algos.SortUtil;
import com.garg.concepts.algos.Util;

/**
 * http://www.ocf.berkeley.edu/~wwu/cgi-bin/yabb/YaBB.cgi?board=riddles_cs;
 * action=display;num=1302533330 Given an integer array, sort the integer array
 * such that the concatenated integer of the result array is max. e.g. [4, 94,
 * 9, 14, 1] will be sorted to [9,94,4,14,1] where the result integer is 9944141
 */
public class MaxConcated 
{
	// If the numbers have same number of digits, then it would be a simple
	// integer comparison. But if number of digits are different in both the
	// numbers, then we have to check the concatenated value.
	public static void main(String[] args) 
	{
		int []a = new int[]{4, 94, 9, 14, 1};
		Util.print(a);
		SortUtil.quickSort(a, new Comparator<Integer>() {
			
			@Override
			public int compare(Integer o1, Integer o2) {
				 return (o2.toString() + o1).compareTo(o1.toString() + o2);
			}
		});
		Util.print(a);
	}
}
