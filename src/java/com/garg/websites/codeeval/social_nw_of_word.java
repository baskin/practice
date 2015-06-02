package com.garg.websites.codeeval;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * https://www.codeeval.com/open_challenges/58/
 */
public class social_nw_of_word
{
	public static void main(String[] args) throws IOException
	{
		String file =  args[0];
		BufferedReader fr = null;
		try {
			fr = new BufferedReader(new FileReader(file));
			String line = null;
			List<String> words = new ArrayList<String>();
			while ((line = fr.readLine()).equals("END OF INPUT")) {
				words.add(line.toLowerCase());
			}
			Set<String> dict = new HashSet<String>();
			while ((line = fr.readLine()) != null) {
				dict.add(line.toLowerCase());
			}
			
			for (String w : words) {
				System.out.println(findFriendCount(w, dict, new HashSet<String>()) - 1);
			}
		}
		finally {
			if (fr != null) {
				fr.close();
			}
		}
	}

	private static int findFriendCount(String w, Set<String> dict, Set<String> visited) 
	{
		// Do a BFS
		if (visited.contains(w)) {
			return 0;
		}
		visited.add(w);
		int sum = 1; // contribution of this 
		for (String s : oneDistance(w)) {
			if (dict.contains(s)) {
				sum += findFriendCount(s, dict, visited);
			}
		}
		return sum;
	}

	private static List<String> oneDistance(String w) 
	{
		List<String> result = new ArrayList<String>();
		// remove
		for (int i = 0; i < w.length(); i++) {
			// exclude char w[i]
			String pre  = w.substring(0, i);
			String post = w.substring(i+1, w.length());
			result.add(pre + post);
		}
		
		// add
		for (int i = 0; i <= w.length(); i++) {
			String pre  = w.substring(0, i);
			String post = w.substring(i, w.length());
			for (int j = 0; j < 26; j++) {
				char c = (char) ('a' + j);
				result.add(pre + c + post);
			}
		}
		
		// substitute
		for (int i = 0; i < w.length(); i++) {
			String pre  = w.substring(0, i);
			String post = w.substring(i+1, w.length());
			for (int j = 0; j < 26; j++) {
				char c = (char) ('a' + j);
				if (c != w.charAt(i)) {
					result.add(pre + c + post);
				}
			}
		}
		return result;
	}

}
