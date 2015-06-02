package com.garg.codeeval;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * http://www.codeeval.com/open_challenges/1/
 */
public class sameend
{
	public static void main(String[] args) throws IOException
	{
		String file = args[0];
		BufferedReader fr = null;
		try {
			fr = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = fr.readLine()) != null) {
				String[] input = line.split(",");
				if (endswith(input[0], input[1])) {
					System.out.println(1);
				}
				else {
					System.out.println(0);
				}
			}
		}
		finally {
			if (fr != null) {
				fr.close();
			}
		}
	}

	private static boolean endswith(String a, String b)
	{
		int aStart = a.length() - b.length();
		if (aStart < 0) {
			return false;
		}
		for (int i = 0; i < b.length(); i++) {
			if (a.charAt(aStart + i) != b.charAt(i)) {
				return false;
			}
		}
		return true;
	}
}
