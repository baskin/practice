package com.garg.websites.codeeval;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * http://www.codeeval.com/open_challenges/1/
 */
public class fizzbuzz
{
	public static void main(String[] args) throws IOException
	{
		String file = args[0];
		BufferedReader fr = new BufferedReader(new FileReader(file));
		String line = null;
		while ((line = fr.readLine()) != null) {
			String[] input = line.split(" ");
			int a = Integer.parseInt(input[0]);
			int b = Integer.parseInt(input[1]);
			int n = Integer.parseInt(input[2]);
			fizzBuzz(a, b, n);
		}
		
	}

	private static void fizzBuzz(int a, int b, int n)
	{
		StringBuilder sb = new StringBuilder();
		boolean leadingSpace = false;
		for (int i = 1; i <= n; i++) {
			if (leadingSpace) {
				sb.append(" ");
			}
			leadingSpace = true;
			boolean fb = false;
			if (i % a == 0) {
				sb.append("F");
				fb = true;
			}
			if (i % b == 0) {
				sb.append("B");
				fb = true;
			}
			if (!fb) {
				sb.append(i);
			}
		}
		System.out.println(sb.toString());
	}
}
