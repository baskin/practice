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
import java.util.Collections;
import java.util.List;

public class I18N
{
    public static void main(String[] args)
    {
        System.out.println(shorten("internationalization"));
    }

    /**
     * 
     * CareerCup http://www.careercup.com/question?id=5733696185303040
     * 
     * i18n (where 18 stands for the number of letters between the first i and
     * the last n in the word “internationalization,”) Wiki it.
     * 
     * Generate all such possible i18n strings for any given string. for eg.
     * "careercup"
     * =>"c7p","ca6p","c6up","car5p","ca5up","care4p","car4up","caree3p"
     * ,"care3up"..till the count is 0 which means its the complete string
     * again.
     */
    public static List<String> shorten(final String text)
    {
        if (null == text || text.length() <= 2) {
            return Collections.singletonList(text);
        }
        
        // at least 3 chars
        char first = text.charAt(0);
        char last = text.charAt(text.length() - 1);
        List<String> middle = permute(text.substring(1, text.length() - 1));
        List<String> result = new ArrayList<String>();
        for (String m : middle) {
            result.add(first + m + last);
        }
        result.add(text);
        return result;
    }

    private static List<String> permute(String text)
    {
        List<String> result = new ArrayList<String>();
        for (int blockSize = 1; blockSize <= text.length(); blockSize++) {
            for (int i = 0; i <= text.length() - blockSize; i++) {
                String v = text.substring(0, i) + String.valueOf(blockSize) + text.substring(i+blockSize);
                result.add(v);
            }
        }
        return result;
    }

}
