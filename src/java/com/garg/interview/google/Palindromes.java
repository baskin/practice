/*
 * Test.java
 *
 * $HeadURL: https://bhupi-practice.googlecode.com/svn/trunk/src/java/com/garg/test/Test.java $
 */

/*
 * Copyright (c) 2010 D. E. Shaw & Co., L.P. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of D. E. Shaw & Co., L.P. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with D. E. Shaw & Co., L.P.
 */

package com.garg.interview.google;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.util.Pair;

import com.garg.prep.ds.Trie;

public class Palindromes
{
    public static void main(String[] args)
    {
        System.out.println(palindromes(Arrays.asList(new String[] {
            "bat", "cat", "dog", "tab", "huf", "fuh", "shoeeo", "hs", "sh", "buildl", "iub"
        })));
    }

    /**
     * 
     * CareerCup http://www.careercup.com/question?id=4879869638868992
     * 
     * You are given a list of word. Find if two words can be joined to-gather
     * to form a palindrome. eg Consider a list {bat, tab, cat} Then bat and tab
     * can be joined to gather to form a palindrome.
     * 
     * Expecting a O(nk) solution where n = number of works and k is length
     * 
     * There can be multiple pairs
     */
    public static List<Pair<String, String>> palindromes(final List<String> words)
    {
        List<Pair<String, String>> result = new ArrayList<Pair<String,String>>();
        // [bat, tab]
        // add each word to a hash set
        // for each word search for its reverse value.
        // doesn't work in case of words like [batt, ab]
        // to fix this
        // do as before, search for reverse value
        // in case of partial match (use a trie instead of a hashmap)
        // see if the remaining portion of the word is a palindrome.
        // if so, the pair is another candidate
        //still doesn't work for words like [ab, batt] (i.e. order of appearance reversed)
        // 
        
        // Build a trie of all the words
        Trie trie = new Trie("");
//        for (String w : words) {
//            trie.add(w, 1);
//        }
        
        for (String w : words) {
            String reverseW = new StringBuilder(w).reverse().toString();
            if (trie.get(reverseW) != null) {
                result.add(new Pair(w, reverseW));
            }
            else {
                trie.add(w, 1);
            }
        }
        return result;
    }
    
    private static boolean isPalindrome(String str) {
        for (int i = 0, j = str.length() - 1; i <= j; i++, j--) {
            if (str.charAt(i) != str.charAt(j)) {
                return false;
            }
        }
        return true;
    }
}
