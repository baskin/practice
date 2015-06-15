
package com.garg.scratchpad;

public class BinaryToHex
{
    public static void main(String[] args)
    {
        System.out.println(compareBinToHex("1000000001111", "100F"));
    }

    private final static String HEX = "0123456789ABCDEF";
    
    // to check if the value of a binary number (passed as a string) equals the
    // hexadecimal representation of a string.
    public static boolean compareBinToHex(String binary, String hex)
    {
        String binHex = binaryToHex(binary);
        return hex.equals(binHex);
    }

    private static String binaryToHex(String binary)
    {
        StringBuilder sb = new StringBuilder();
        // e = end, s = start of 4 blocks of binary digits (= 1 hex digit)
        for (int e = binary.length() - 1; e >= 0; e -= 4) {
            int s = Math.max(e - 3, 0); // IMP e - 3 and NOT e - 4
            int decimalValue = 0;
            for (int i = s; i <= e; i++) {
                int binaryValue = binary.charAt(i) - '0';
                decimalValue = (decimalValue << 1) + binaryValue;
            }
            sb.append(HEX.charAt(decimalValue));
        }
        sb.reverse(); // IMP
        return sb.toString();
    }
}
