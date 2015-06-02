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

package com.garg;

import java.util.ArrayList;
import java.util.List;

public class Practice
{
    public static void main(String[] args)
    {
    }

    // Write a method to count the number of 2s that appear in all the numbers
    // between 0 and n (inclusive).
    // e.g. n = 25
    // 2 12 20 21 22 23 24 25 ... = 9
    public static int count(int limit)
    {
        List<Integer> digits = new ArrayList<Integer>();
        while (limit > 0) {
            digits.add(limit % 10);
            limit = limit / 10;
        }
        // Now digit(i) = digit at (10^i)th place.
        // e.g. for limit = 1594
        // (4, 9, 5, 1)
        return count(digits);
    }

    private static int count(List<Integer> digits)
    {
        int count = 0;
        for (int i = 0; i < digits.size(); i++) {
            count += digits.get(i) * count(i, digits);
        }
        return count;
    }
    
    private static int count(int tensPlaces, List<Integer> limit) {
        digit = limit.get(tensPlaces);
        if (tensPlaces == 0) {
            return digit >= 2 ? 1 : 0;
        }
        int count = 
    }
}
