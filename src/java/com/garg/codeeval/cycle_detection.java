package com.garg.codeeval;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * http://www.codeeval.com/open_challenges/1/
 */
public class cycle_detection
{
	public static void main(String[] args) throws IOException
	{
		String file = args[0];
		BufferedReader fr = null;
		try {
			fr = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = fr.readLine()) != null) {
				cycle(line.split(" "));
				System.out.println();
			}
		}
		finally {
			if (fr != null) {
				fr.close();
			}
		}
	}

	//  2 0 6 3 1 ... 6 3 1..
	//  0 -> 6 .
	//  1 -> 6 .
	//  2 -> 0 .
	//  3 -> 1 .
	//  4 -> 4
	//  5 -> 5
	//  6 -> 3 .
	private static void cycle(String[] str)
	{
		if (str == null || str.length < 2) {
			return;
		}
		int[] dataset = new int[100];
		for (int i = 0; i < 100; i++) {
			dataset[i] = -1;
		}
		
		int prev = Integer.parseInt(str[0]);
		int startCycleIndex = -1;
		for (int i = 1; i < str.length; i++) {
			int cur  = Integer.parseInt(str[i]);
			if (dataset[prev] != -1 && startCycleIndex == -1) {
				startCycleIndex = prev;
			}
			dataset[prev] = cur;
			prev = cur;
		}
		if (startCycleIndex != -1) {
			// already visited. cycle detected.
			printCycle(dataset, startCycleIndex);
		}
	}

	private static void printCycle(int[] set, int startIndex) 
	{
		System.out.print(startIndex + " ");
		int cur  = set[startIndex];
		while (cur != startIndex) {
			System.out.print(cur + " ");
			cur = set[cur];
		}
	}
}