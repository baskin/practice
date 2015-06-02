package com.garg.codeeval;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * http://codeeval.com/open_challenges/37/
 */
public class pangrams
{
	public static void main(String[] args) throws IOException
	{
		String file = args[0];
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(fetchPangrams(line));
			}
		}
		finally {
			if (br != null) {
				br.close();
			}
		}
	}

	/**
	 * Fetch the pangrams of the sentence.
	 */
	private static String fetchPangrams(String line)
	{
		boolean[] alphabet = new boolean[26];
		// Ignore non-english alphabets
		for (int i = 0; i < line.length(); i++) {
			int index = Character.toLowerCase(line.charAt(i)) - 'a';
			if (index >= 0 && index < 26) {
				alphabet[index] = true;
			}
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < alphabet.length; i++) {
			if (!alphabet[i]) {
				sb.append((char)('a' + i));
			}
		}
		return sb.length() == 0 ? null : sb.toString();
	}
}
