package com.garg.concepts.ds;

/**
 * A <a href=http://en.wikipedia.org/wiki/Trie>trie<a> (re<b>trie</b>val), or
 * prefix tree, is an ordered tree data structure that is used to store an
 * associative array where the keys are usually strings. Unlike a binary search
 * tree, no node in the tree stores the key associated with that node; instead,
 * its position in the tree shows what key it is associated with. All the
 * descendants of a node have a common prefix of the string associated with that
 * node, and the root is associated with the empty string.
 */
public class Trie
{
	private static final int ALPHABET_SIZE = 26;

	// The key e.g. friend name.
	private final String prefix;
	// Value vould be e.g. mobile entry, dictionary meaning.
	// Value will be null for internal nodes which do not have any associcated,
	// well, value.
	private Object value;
	private final Trie[] children = new Trie[ALPHABET_SIZE];

	public Trie(String pref)
	{
		this(pref, null);
	}

	public Trie(String pref, Object val)
	{
		prefix = pref;
		value = val;
	}

	public Object add(String str, Object val)
	{
		if (str == null || str.isEmpty()) {
			return null;
		}

		str = str.toLowerCase();
		char c = str.charAt(0);
		Trie subTrie = children[c - 'a'];
		if (subTrie == null) {
			subTrie = new Trie(prefix + c);
			children[c - 'a'] = subTrie;
		}

		if (str.length() == 1) {
			Object oldVal = subTrie.value;
			subTrie.value = val;
			return oldVal;
		}
		else {
			return subTrie.add(str.substring(1), val);
		}
	}

	public Object get(String str)
	{
		if (str == null || str.isEmpty()) {
			return null;
		}
		str= str.toLowerCase();
		char c = str.charAt(0);
		Trie subTrie = children[c - 'a'];
		if (subTrie != null) {
			if (str.length() == 1) {
				return subTrie.value;
			}
			else {
				return subTrie.get(str.substring(1));
			}
		}
		return null;
	}
	
	public void sort()
	{
		// Think: In trie a PREORDER traversal will yield a sorted order.
		// Visit parent
		if (this.value != null) {
			System.out.print(this.prefix + " ");
		}
		for (Trie child : children) {
			if (child != null) {
				child.sort();
			}
		}
	}
	
	public static void main(String[] args)
	{
		// Insert a bunch of key/value pairs.
        Trie trie = new Trie("");
        trie.add("Java", "rocks");
        trie.add("Jackal", "sings");
        trie.add("Melinda", "too");
        trie.add("Moo", "cow"); // Will collide with "Melinda".
        trie.add("Moon", "walk"); // Collides with "Melinda" and turns "Moo" into a prefix.
        
        // Test for inserted, nonexistent, and deleted keys.
        System.out.println("Java = " + trie.get("Java"));
        System.out.println("Melinda = " + trie.get("Melinda"));
        System.out.println("Moo = " + trie.get("Moo"));
        System.out.println("Moon = " + trie.get("Moon"));
        System.out.println("Mo = " + trie.get("Mo")); // Should return null.
        System.out.println("Moose = " + trie.get("Moose")); // Never added so should return null.
        System.out.println("Nothing = " + trie.get("Nothing")); // Ditto.
        trie.add("Moo", null); // Removes this prefix entry. (Special case to test internal logic).
        System.out.println("After removal, Moo = " + trie.get("Moo")); // Should now return null.
        
        trie.sort();
	}
}
