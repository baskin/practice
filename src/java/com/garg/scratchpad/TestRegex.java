/*
 * Test.java
 *
 * $HeadURL: https://bhupi-practice.googlecode.com/svn/trunk/src/java/com/garg/test/TestRegex.java $
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
