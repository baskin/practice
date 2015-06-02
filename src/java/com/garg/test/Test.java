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

package com.garg.test;

public class Test
{
	public static void main(String[] args)
	{
		byte[] codes = new byte[Character.MAX_VALUE];		
		
		int conflictCount = 0;
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			int code = hashCode(i);
			int byteIndex = code >> 3;
	        int offset = code % 8;
			if ((codes[byteIndex] &  (1 << offset)) == 0) {
		        codes[byteIndex]  |= (1 << offset);
			}
			else {
				conflictCount++;
			}
		}
		System.out.println("Found " + (1.0 * conflictCount)/Integer.MAX_VALUE + " conflicts");
	}

	private static char hashCode(int i)
	{
//		return (int)(i << 32);
//		return (int)(i >> 32);
		return (char) (i << 16);
	}
}
