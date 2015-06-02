package com.garg.concepts.algos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

/**
 * The greedy huffman algo.
 */
public class Huffman
{
	private final char[] alphabet;
	private final int[] freq;
	
	private static class Node 
	{
		private final char alphabet;
		private final int freq;
		private Node parent, left, right;
		
		private Node(char a, int f) {
			alphabet = a;
			freq = f;
		}
	}

	public Huffman(char[] alphabet, int[] freq)
	{
		this.alphabet = alphabet;
		this.freq = freq;
	}
	

	private Map<Character, String> map()
	{
		PriorityQueue<Node> heap = 
			new PriorityQueue<Node>(
				alphabet.length, 
				new Comparator<Node>()
				{
					@Override
					public int compare(Node o1, Node o2)
					{
						return o1.freq > o2.freq ? 1 : (o1.freq == o2.freq ? 0 : -1);  
					}
				});
		
		List<Node> leaves = new ArrayList<Node>();
		for (int i = 0; i < alphabet.length; i++) {
			Node node = new Node(alphabet[i], freq[i]);
			leaves.add(node);
			heap.add(node);
		}
		
		while (heap.size() > 1) {
			Node left  = heap.poll();
			Node right = heap.poll();
			Node root = new Node('0', left.freq + right.freq);
			left.parent = right.parent = root;
			root.left = left;
			root.right = right;
			heap.add(root);
		}
		assert heap.size() == 1;
		Map<Character, String> map = new HashMap<Character, String>();
		for (Node n : leaves) {
			map.put(n.alphabet, search(n));
		}
		return map;
	}
	
	public String encode(String msg) 
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < msg.length(); i++) {
			String code = map().get(msg.charAt(i));
			if (code == null) {
				return null;
			}
			sb.append(code);
		}
		return sb.toString();
	}
	
	public String decode(String msg) 
	{
		Map<String, Character> reverseMap = new HashMap<String, Character>();
		for (Entry<Character, String> e : map().entrySet()) {
			reverseMap.put(e.getValue(), e.getKey());
		}
		
		StringBuilder sb = new StringBuilder();
		int i = 0;		
		while (i < msg.length()) {
			StringBuffer code = new StringBuffer();
			while (!reverseMap.containsKey(code.toString())) {
				code.append(msg.charAt(i));
				i++;
			}
			sb.append(reverseMap.get(code.toString()));
		}
		return sb.toString();
	}
	
	/**
	 * Build the search path. 0 for left, 1 for right.
	 */
	private String search(Node node) 
	{
		StringBuilder sb = new StringBuilder();
		while (node.parent != null) {
			Node parent = node.parent;
			if (parent.left == node) {
				sb.insert(0,'0');
			}
			else {
				sb.insert(0,'1');
			}
			node = parent;
		}
		return sb.toString();
	}
	
	public static void main(String[] args)
	{
		int[] freq      = {300, 20, 200, 90, 710};
		char[] alphabet = {'a', 'b', 'c', 'd', 'e'};
		Huffman huff = new Huffman(alphabet, freq);
		for (Entry<Character, String> e : huff.map().entrySet()) {
			System.out.println(e);
		}
		
		String msg = "abbcccaddebbceeeeeeeadb";
		String encodedMsg = huff.encode(msg);
		System.out.println(msg);
		System.out.println("Msg size:" + msg.getBytes().length * 8 + " bits");
		System.out.println(encodedMsg);
		System.out.println("EncMsg size:" + encodedMsg.length() + " bits");
		System.out.println("Compression achieved: " + msg.getBytes().length * 8.0/encodedMsg.length());
		System.out.println(huff.decode(encodedMsg));
	}
}
