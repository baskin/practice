package com.garg.websites.codeeval;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * http://www.codeeval.com/open_challenges/1/
 */
public class longestlines
{
	public static void main(String[] args) throws IOException
	{
		String file = args[0];
		BufferedReader fr = null;
		try {
			fr = new BufferedReader(new FileReader(file));
			String line = null;
			String firstLine = fr.readLine();
			int n = Integer.parseInt(firstLine);
			String max = "", secondMax = "";
			while ((line = fr.readLine()) != null) {
				if (line.length() > max.length()) {
					secondMax = max;
					max = line;
				}
				else if (line.length() > secondMax.length()) {
					secondMax = line;
				}
			}
			System.out.println(max);
			System.out.println(secondMax);
		}
		finally {
			if (fr != null) {
				fr.close();
			}
		}
	}
}
