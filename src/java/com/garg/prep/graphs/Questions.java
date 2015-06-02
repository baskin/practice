package com.garg.prep.graphs;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.POS;

public class Questions
{

	private static IDictionary ourDictionary;
	
	static {
		String path = 
			"C:\\D\\work\\workspace\\bhupi-practice\\trunk\\external\\WNdb-3.0\\dict";
		URL url = null;
		try {
			url = new URL("file", null, path);
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		ourDictionary = new Dictionary(url);
		ourDictionary.open();
	}
	
	private static boolean exists(String word) 
	{
		for (POS pos : POS.values()) {
			IIndexWord wd = ourDictionary.getIndexWord(word, pos);
			if (wd != null) {
				return true;
			}
		}
		return false;
	}
	
	private static List<String> oneEditDictWords(String word)
	{
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < word.length(); i++) {
			char []wordArray = word.toCharArray();
			for (char j = 'A'; j <= 'Z'; j++) {
				if (j != word.charAt(i)) {
					wordArray[i] = j;
					String newWord = new String(wordArray);
					if (exists(newWord)) {
						result.add(newWord);
					}
				}
			}
		}
		return result;
	}
	
	private static List<String> twoEditDictWords(String word)
	{
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < word.length(); i++) {
			for (int j = 0; j < word.length(); j++) {
				if (i == j) {
					continue;
				}
				char []wordArray = word.toCharArray();
				for (char k = 'A'; k <= 'Z'; k++) {
					for (char l = 'A'; l <= 'Z'; l++) {
						if (k != word.charAt(i) && l != word.charAt(j)) {
							wordArray[i] = k;
							wordArray[j] = l;
							String newWord = new String(wordArray);
							if (exists(newWord)) {
								result.add(newWord);
							}
						}
					}
				}
			}
		}
		return result;
	}

	public static void transform(String source, String dest)
	{
		source = source.toUpperCase();
		dest = dest.toUpperCase();
		
		if (source.length() != dest.length()) {
			System.out.println("No path between '" + source + "' and '" + dest + "'.");
			return;
		}
		
		Queue<String> queue = new LinkedList<String>();
		Set<String> visited = new HashSet<String>();
		Map<String, String> backTrack = new HashMap<String, String>();
		queue.add(source);
		visited.add(source);
		
		String word = null;
		while (! queue.isEmpty()) {
			word = queue.poll();
			for (String w : oneEditDictWords(word)) {
				if (w.equals(dest)) {
					// Success.
					List<String> order = new ArrayList<String>();
					order.add(dest);
					String lastWord = word;
					while (lastWord != null) {
						order.add(0, lastWord);
					    lastWord = backTrack.get(lastWord);
					}
					for (String s : order) {
						System.out.print(s + " --> ");
					}
					System.out.println("[DONE].");
					return;
				}
				else {
					if (! visited.contains(w)) {
					    queue.add(w);
					    visited.add(w);
					    backTrack.put(w, word);
					}
				}
			}
		}
		System.out.println("No path between '" + source + "' and '" + dest + "'.");
	}
	
	public static void main(String[] args)
	{
		transform("damp", "like");
		transform("damp", "blue");
//		System.out.println(oneEditDictWords("LAMP"));
	}
}