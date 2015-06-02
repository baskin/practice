package com.garg.codeeval;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * https://www.codeeval.com/open_challenges/77/
 */
public class davincy
{
	public static void main(String[] args) throws IOException
	{
		String file = args[0];
		BufferedReader fr = null;
		try {
			fr = new BufferedReader(new FileReader(file));
			String line = null;
			// one test case per line.
			while ((line = fr.readLine()) != null) {
				// fragments are ';' separated
				String[] input = line.split(";");
				System.out.println(reassemble(input));
			}
		}
		finally {
			if (fr != null) {
				fr.close();
			}
		}
	}

	private static String reassemble(String[] input) 
	{
		// Attempt one: Brute force.
		int length = input.length;
		while (length > 1) {
			int maxOverlap = 0;
			int indexA = -1, indexB = -1;
			for (int i = 0; i < length; i++) {
				for (int j = i+1; j < length; j++) {
					String a = input[i];
					String b = input[j];
					int overlap = getOverlap(a, b);
					if (overlap > maxOverlap) {
						maxOverlap = overlap;
						indexA = i;
						indexB = j;
					}
				}
			}
			int removedIndex = merge(input, indexA, indexB, maxOverlap);
			length--;
			swap(input, removedIndex, length);
		}
		return input[0];
	}

	private static void swap(String[] a, int i, int j) 
	{
		String temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	private static int getOverlap(String a, String b) 
	{
		// let a be smaller of the 2.
		if (a.length() > b.length()) {
			String tmp = a;
			a = b;
			b = tmp;
		}
		if (b.contains(a)) {
			return a.length();
		}
		
		// try a.b
		// abcdef  defghijklmn
		int overlap = 0;
		for (int i = 1; i < a.length(); i++) {
			if (a.substring(0, i).equals(b.substring(b.length() - i))) {
				overlap = i;
			}
			if (b.substring(0, i).equals(a.substring(a.length() - i))) {
				overlap = i;
			}
		}
		
		return overlap;
	}

	private static int merge(String[] input, int indexA, int indexB, int maxOverlap) 
	{
		String a = input[indexA];
		String b = input[indexB];
		
		if (maxOverlap == a.length()) {
			return indexA;
		}
		if (maxOverlap == b.length()) {
			return indexB;
		}
		
		if (a.substring(0, maxOverlap).equals(b.substring(b.length() - maxOverlap))) {
			input[indexB] = b + a.substring(maxOverlap);
			return indexA;
		}
		else if (a.substring(a.length() - maxOverlap).equals(b.substring(0, maxOverlap))) {
			input[indexA] = a + b.substring(maxOverlap);
			return indexB;
		}
		else {
			throw new AssertionError(
				String.format("Programming error: '%s', '%s', '%d' ", a, b, maxOverlap));
		}
	}
}
