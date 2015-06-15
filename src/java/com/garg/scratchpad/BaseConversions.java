
package com.garg.scratchpad;

public class BaseConversions
{
    public static void main(String[] args)
    {
        System.out.println(convertBase("10011", 2, 10));
        System.out.println(convertBase("10011", 2, 3));
        System.out.println(convertBase("10011", 2, 16));
    }
    
    public static String convertBase(String text, int fromBase, int toBase) {
        checkBase(fromBase);
        checkBase(toBase);
        int overallDecimal = 0;
        for (int i = 0; i < text.length(); i++) {
            int digitDecimal = decimalValueFromChar(text.charAt(i));
            if (digitDecimal == -1) {
                throw new IllegalArgumentException("Invalid text");
            }
            overallDecimal = overallDecimal * fromBase + digitDecimal;
        }
        System.out.println("[INFO] decimal value=" + overallDecimal);
        int q = overallDecimal; // quotient
        StringBuilder newText = new StringBuilder();
        while (q > 0) {
            int r = q % toBase;
            q = q/toBase;
            newText.append(charFromDecimalValue(r));
        }
        newText.reverse();
        return newText.toString();
    }

    private static void checkBase(int base)
    {
        if (base < 2 || (base > 10 && base != 16)) {
            throw new IllegalArgumentException("Invalid base");
        }
    }

    private static char charFromDecimalValue(int r)
    {
        if (r >= 0 || r <= 9) {
            return (char) ('0' + r);
        }
        if (r >= 10 || r <= 15) {
            return (char) ('A' + r - 10);
        }
        return '\0';
    }

    private static int decimalValueFromChar(char c)
    {
        if (c >= '0' || c <= '9') {
            return c - '0';
        }
        if (c >= 'A' || c <= 'F') {
            return c - 'A' + 10;
        }
        if (c >= 'a' || c <= 'f') {
            return c - 'a' + 10;
        }
        return -1;
    }
}
