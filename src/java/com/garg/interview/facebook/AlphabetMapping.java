package com.garg.interview.facebook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlphabetMapping
{
    private final static String MAP = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    public static void main(String[] args)
    {
        System.out.println(mappings("123"));
        System.out.println(mappings("111"));
        System.out.println(mappings("3451517242"));
    }

    /**
     * 
     * CareerCup http://www.careercup.com/question?id=5705619461898240
     * 
     * Given a mapping of alphabets to integers as follows:
     * 
     * 1 = A 2 = B 3 = C ... 26 = Z
     * 
     * Given any combination of the mapping numbers as string, return the number
     * of ways in which the input string can be split into sub-strings and
     * represented as character strings. For e.g. given "111" -> "AAA", "AK",
     * "KA" -> 3 Valid combinations are ({1,1,1}, {1,11},{11,1}) = 3 "11" ->
     * "AA", "K" -> 2 Valid combinations are ({1,1},{11}) = 2 "123" -> "ABC",
     * "LC", "AW" -> 3 Valid combinations are ({1,2,3},{1,23},{12,3}) = 3
     * 
     * You don't have to return all the mappings, only the number of valid
     * mappings.
     * 
     */
    public static List<String> mappings(final String text)
    {
        // 123432
        // 1, 23432
        // 12, 3432
        if (text == null || text.isEmpty()) {
            return Collections.singletonList("");
        }
        if (text.length() == 1) {
            int val = text.charAt(0) - '1';
            Assert.isTrue(val >= 0 && val < 10);
            return Collections.singletonList(String.valueOf(MAP.charAt(val)));
        }
        
        int first = text.charAt(0) - '1';
        Assert.isTrue(first >= 0 && first < 10);
        char firstChar = MAP.charAt(first);
        List<String> subMappings = mappings(text.substring(1));
        
        List<String> result = new ArrayList<String>();
        for (String m : subMappings) {
            result.add(firstChar + m);
        }
        
        int second = text.charAt(1) - '1';
        Assert.isTrue(first >= 0 && first < 10);
        int val = (first + 1) * 10 + second + 1;
        if (val <= 26) {
            firstChar = MAP.charAt(val - 1);
            subMappings = mappings(text.substring(2));
            
            for (String m : subMappings) {
                result.add(firstChar + m);
            }
        }
        return result;
    }
}
