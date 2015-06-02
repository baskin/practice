package com.garg.codeeval;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * http://www.codeeval.com/open_challenges/1/
 */
public class substring
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
				System.out.println(match(input[0], input[1]));
			}
		}
		finally {
			if (fr != null) {
				fr.close();
			}
		}
	}

	// heello,ello
	// CodeEval,C*Eval
	private static boolean match(String str, String reg)
	{
		if (reg.length() < 1) {
			return false;
		}
		char c = reg.charAt(0);
		int j = 0, i = 1;
		while (j < str.length()) {
			while(j < str.length() && str.charAt(j) != c) j++;
			if (j < str.length()) {
				int jj = j+1;
				// j points to first occurance of c in str.
				for (;i < reg.length() && jj < str.length(); i++, jj++) {
					if (reg.charAt(i) != str.charAt(jj)) {
						if (reg.charAt(i) == '*') {
							while((c = reg.charAt(i)) == '*') i++;
						}
						break;
					}
				}
				if (i == reg.length()) {
					return true;
				}
			}
			j++;
		}
		return false;
	}
}
