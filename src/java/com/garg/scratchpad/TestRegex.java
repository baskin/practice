
package com.garg.scratchpad;

public class TestRegex
{
    public static void main(String[] argv)
    {
    	 String wardSuffix = "/" + 77;
    	 String snapNs = argv[0];
    	 
    	 if (snapNs.charAt(snapNs.length() - 1) == '/') {
    		 snapNs = snapNs.substring(0, snapNs.length() - 1);
    	 }
    	 if (snapNs.matches(".*/[0-9]+$")) {
             int wardStartIndex = snapNs.lastIndexOf('/');
             snapNs = snapNs.substring(0, wardStartIndex);
             System.out.println(snapNs);
         }
         snapNs += wardSuffix;
         System.out.println(snapNs);
    }
}
