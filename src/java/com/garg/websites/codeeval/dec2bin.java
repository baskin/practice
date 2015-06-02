package com.garg.websites.codeeval;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * http://www.codeeval.com/open_challenges/1/
 */
public class dec2bin
{
	public static void main(String[] args) throws IOException
	{
		String file = args[0];
		BufferedReader fr = null;
		try {
			fr = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = fr.readLine()) != null) {
				System.out.println(toBinary(Integer.parseInt(line)));
			}
		}
		finally {
			if (fr != null) {
				fr.close();
			}
		}
	}

	private static String toBinary(int n)
	{
		if (n < 1) {
			return "0";
		}
		StringBuilder sb = new StringBuilder();
		while (n != 0) {
			sb.insert(0, (n&1) == 0 ? 0 : 1);
			n >>= 1;
		}
		return sb.toString();
	}
}
