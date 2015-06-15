package com.garg.concepts.algos;

public class Patternmatching
{
	private static int[][] finiteAutomataTransitionFunction(String pattern) 
	{
	    // number of states = pattern_length + 1.
	    // one transition for each char of pattern.
	    // e.g. if P = "abc"
	    // 0 --a--> 1 --b--> 2 --c--> 3
	    // Total of 4 states.
	    // 0 is starting state, 3 is accepting state.
		int numOfStates = pattern.length() + 1;
		int alphabetSize = 26;
		
		int[][] transition = new int[numOfStates][alphabetSize];
		
		// Each state has an accepting prefix.
		// e.g. in the above.
		// state 1 -> a, 2 -> ab, 3 -> abc
		// in other words, state -> pattern.substring(0, state)
		for (int state = 0; state < numOfStates; state++) {
			for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
				int k = Math.min(state + 1, numOfStates  - 1);
				String current = pattern.substring(0, state) + alphabet;
				
				// Look for the longest prefix of the pattern that is also a suffix of current. 
				while (!current.endsWith(pattern.substring(0, k))) {
					k--;
				}
				transition[state][alphabet - 'a'] = k;
			}
		}
		return transition;
	}
	
	public static void finiteAutomatoMatch(String text, String pattern)
	{
	    // Preprocessing for the given pattern and alphabet.
	    // O(pattern_size * alphabet_size)
		int[][] transition = finiteAutomataTransitionFunction(pattern);
		int state = 0;
		
		// Matching. O(text_length)
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			state = transition[state][c - 'a'];
			if (state == pattern.length()) {
				System.out.println("Found match at starting index " + (i+1 - pattern.length()));
			}
		}
	}
	
	public static void main(String[] args)
	{
		finiteAutomatoMatch("abghpkeptkeplkkeepekep", "kep");
	}
}
