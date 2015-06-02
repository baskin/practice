package com.garg.websites.interviewstreet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 
 * Shortest sub-segment.
 * <p>
 * Given a paragraph of text, and a list of k words, write a program to find the
 * first shortest sub-segment that contains each of the given k words at least
 * once. The length of a segment is the number of words included; a segment is
 * said to be shorter than another if it contains less words than the other
 * structure. Ignore characters other than [a-z][A-Z] in the text. Comparison
 * between the strings should be case-insensitive.
 * <p>
 * https://amazon.interviewstreet.com/challenges/dashboard/#problem/4fd648244715d
 * 
 * @author bgarg
 */
public class InterviewStreet
{
    public static void main(final String args[]) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String text = br.readLine();
        int n = Integer.parseInt(br.readLine());
        Set<String> words = new HashSet<String>(n);
        for (int i = 0; i < n; i++) {
            words.add(br.readLine().toLowerCase());
        }
        String [] para = text.split("\\s+");
        sanitize(para);
        System.out.println(shortestSegment(para, words));
    }

    private static void sanitize(final String[] para)
    {
        for (int i = 0; i < para.length; i++) {
            para[i] = para[i].replaceAll("[^a-zA-Z]", "");
        }
    }

    private static String shortestSegment(final String[] para, final Set<String> words)
    {
        // Keep an
        Map<String, Integer> indices = new HashMap<String, Integer>(words.size());
        
        int startIndex = -1;
        // start and end indices for the answer segment (with minimum length)
        int minStart = -1, minEnd = -1;
        
        for (int i = 0; i < para.length; i++) {
            String cur = para[i].toLowerCase();
            if (! words.contains(cur)) {
                // until the next interest is found.
                continue;
            }
            // current word is an interest word.

            if (startIndex == -1) {
                // begin a new candidate segment
                startIndex = i;
            }
            indices.put(cur, i);
            
            if (cur.equals(para[startIndex].toLowerCase())) {
                // start word found again.
                // reset start index.
                startIndex = Collections.min(indices.values());
            }
            
            if (indices.size() == words.size()) {
                // We have all the words. This is a candidate solution.
                
                // compare and update bookkeeping records.
                if (minStart == -1 || (minEnd - minStart > i - startIndex)) {
                    minStart = startIndex;
                    minEnd   = i;
                }
                
                // reset the candidate.
                // remove the candidate start.
                indices.remove(para[startIndex].toLowerCase());
                startIndex = -1;
                // move the next start index up ahead in indices.
                if (! indices.isEmpty()) {
                    startIndex = Collections.min(indices.values());
                }
            }
        }
        
        if (minStart == -1) {
            // no solution exists.
            return "NO SUBSEGMENT FOUND";
        }
        String[] segment = Arrays.copyOfRange(para, minStart, minEnd + 1);
        return String.join(" ", segment);
    }
}
