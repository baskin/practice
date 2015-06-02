package com.garg.dsalgodotcom;

import java.util.Arrays;

/**
 * You are given a character array like this a3b1c1d1e4f0g11. You will have to
 * expand the array by repeating the characters denoted by the following
 * numbers. For example the above character array will be expanded to
 * aaabcdeeeeggggggggggg. The given array will have more than enough trailing
 * spaces such that you can modify the array in place.
 * 
 * @author bgarg
 */
public class ExpandArray
{
    public static int expand(char[] a, int l)
    {
        if (l < 2) {
            return l;
        }
        return expandInner(a, l, 0, 0);
    }

    // a3b1c1d1e4f0g11
    /**
     * Expand the array a[readFrom..] and return the modified array which retains values
     * a[0..writeTo-1] but updates the remaining according to the expansion rules.
     */
    private static int expandInner(char[] a, int l, int readFrom, int writeTo)
    {
        if (readFrom >= l) {
            // we've already reached the end of the array.
            // nothing more to expand.
            return writeTo;
        }
        if (writeTo >= a.length) {
            // shouldn't happen
            assert false;
        }
        char c = a[readFrom++];
        int count = 0;
        while (isDigit(a[readFrom])) {
            count = count * 10 + (a[readFrom++] - '0'); 
        }
        int length = expandInner(a, l, readFrom, writeTo + count);
        for (int i = 0; i < count; i++) {
            a[writeTo + i] = c;
        }
        return length;
    }

    private static boolean isDigit(char c)
    {
        int diff = c - '0';
        return diff >= 0 && diff <= 9;
    }
    
    /**
     * In-place expansion wo using extra space.
     * <p>
     * only problem is when you read x chars and are to write y (<x) characters
     * e.g. c0 -> read 2, write 0
     *      c1 -> read 2, write 1
     * <p>
     * So we do the following
     * 1. In first pass, compress the array towards left, removing 0 counts and expanding 1 counts
     * e.g. a3b1c1d1e4f0g11 -> a3bcde4g11
     * 2. In next pass, you should start from index = total count (3+1+1+1+4+11) in the above
     * and start expanding chars from right.
     */
    public static int inplaceExpand(char[] a, int l)
    {
        // First pass
        int read = 0, write = 0;
        while (read < l) {
            int c = a[read++];
            
        }
        return -1;
    }
    
    public static void main(String[] args)
    {
        char [] arr = "a3b1c1d1e4f0g11y3".toCharArray();
        char [] a = Arrays.copyOf(arr, 100);
        int length = expand(a, arr.length);
        System.out.println("'" + String.valueOf(Arrays.copyOf(a, length)) + "'");
    }
}
