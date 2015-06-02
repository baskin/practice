/**
 * 
 */
package com.garg.concepts.algos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author gargb
 *
 */
public class Combinatorics 
{
	
	/**
	 * nCr = (n-1)C(r-1) + (n-1)C(r)
	 */
	public static List<String> combine(String str, int r)
	{
		if (str == null) {
			return null;
		}
		
		int n = str.length();
		List<String> result = new ArrayList<String>();
		if (n < r) {
			throw new IllegalArgumentException();
		}
		
		// nCr = nC0 = 1
		if (n == r) {
			result.add(str);
			return result;
		}
		if (r == 0) {
			// empty
			result.add("");
			return result;
		}
		
		char include = str.charAt(0);
		List<String> r1 = combine(str.substring(1), r - 1);
		for (String s : r1) {
			result.add(s + include);
		}
		result.addAll(combine(str.substring(1), r));
		return result;
	}
	
	public static List<String> permute(String str) 
	{
		int length = str.length();
		List<String> result = new ArrayList<String>();

		// Base case.
		if (length == 1) {
			result.add(str);
			return result;
		}
		
		List<String> subPermutations = permute(str.substring(1));
		char c = str.charAt(0);
		for (String subPerm : subPermutations) {
			for (int i = 0; i <= subPerm.length(); i++) { 
				result.add(insert(c, subPerm, i));
			}
		}
		return result;
	}
	
	private static String insert(char ch, String subPerm, int index)
	{
		StringBuilder sb = new StringBuilder(subPerm);
		sb.insert(index, ch);
		return sb.toString();
	}
	
	public static Set<String> NParenthesisCombinations(int n)
	{
		if (n < 1) {
			throw new IllegalArgumentException("Argument should be > 0. Provided " + n);
		}
		Set<String> result = new HashSet<String>();
		// Base case.
		if (n == 1) {
			result.add("()");
			return result;
		}
		
		Set<String> subCombinations = NParenthesisCombinations(n - 1);
		for (String combo : subCombinations) {
			// Place the opening parenthesis.
			for (int i = 0; i <= combo.length(); i++) {
				// Place the closing parenthesis at all places after "i".
				String tmp = insert('(', combo, i);
				if (i == tmp.length()) {
					result.add(tmp + ")");
					continue;
				}
				for (int j = i + 1; j <= tmp.length(); j++) {
					result.add(insert(')', tmp, j));
				}
			}
		}
		return result;
	}
	
	public static int combinationsToNCents(int total, int denom)
	{
		int nextDenom = 0;
		switch (denom) {
		case 25:
			nextDenom = 10;
			break;
		case 10:
			nextDenom = 5;
			break;
		case 5:
			nextDenom = 2;
			break;
		case 2:
			nextDenom = 1;
			break;
		case 1:
			return 1;
		}
		
		int ways = 0;
		for (int i = 0; i*denom < total; i++) {
			ways += combinationsToNCents(total - i*denom, nextDenom);
		}
		return ways;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		String s = "abcde";
		
		List<String> combinations = combine(s, 4);
		System.out.println(combinations.size() + " combinations of " + s + ", 2 at a time");
		for (String c : combinations) {
			System.out.println("'" + c + "'");
		}
		
//		List<String> permutations = permute(s);
//		System.out.println(permutations.size() + " permutations of " + s);
//		for (String p : permutations) {
//			System.out.println(p);
//		}
//		
//		Set<String> nCombos = NParenthesisCombinations(4);
//		System.out.println(nCombos.size() + " n-pairs parenthesis combos with 3 pairs");
//		for (String p : nCombos) {
//			System.out.println(p);
//		}
//		
//		System.out.println(combinationsToNCents(10, 5));
	}
}
