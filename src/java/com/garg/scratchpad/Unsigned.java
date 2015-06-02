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

package com.garg.scratchpad;

/**
 * If you are dealing purely with numbers that are 32 bits or smaller � i.e. the
 * size of a Java int � then you can perform unsigned arithmetic reliably by
 * using a long to perform the arithmetic. Only the top bit of a long is
 * interpreted as the sign bit. So provided you keep to the bottom bits, you
 * will effectively be performing unsigned arithmetic. We just need to be
 * careful of two things:
 * 
 * To cast a signed int to an unsigned value in the bottom 32 bits of a long, we
 * need to AND with 0xffffffffL (232-1� or the number with all 32 bottom bits
 * set). After an operation on longs that we're treating as unsigned ints, we
 * should either cast back to an int, or and with 0xffffffffL if we want to keep
 * the (unsigned) value in a long.
 * 
 * @author gargb-admin
 * 
 */
public class Unsigned
{
	// 32 bit arithmetic 
	
	public static int unsignedDiv(int i1, int i2)
	{
		long l1 = i1 & 0xffffffffL, l2 = i2 & 0xffffffffL;
		return (int) (l1 / l2);
	}

	/**
	 * We can perform an unsigned 32-bit comparison by "casting" the numbers to
	 * a long (remembering to AND with the bottom 32 bits as mentioned), then
	 * perform the comparison on the longs:
	 */
	public static boolean lessThanUnsigned(int n1, int n2)
	{
		long l1 = n1 & 0xffffffffL;
		long l2 = n2 & 0xffffffffL;
		System.out.println(l1 + " unsigned < " + l2);
		return l1 < l2;
	}

	public static void main(String[] args) throws InterruptedException
	{
		// 32 bit arithmetic ------------------------------
		int i = -4;
		System.out.println("Integer: " + i);
		// as 2's complement.
		System.out.println("Binary str (int): " + Integer.toBinaryString(i));
		
		long l = i;
		// l is still -4.
		System.out.println("Cast to long: " + l);
		System.out.println("Binary str (long): " + Long.toBinaryString(l));
		
		l = (i & 0xffffffffL); // notice 'L'
		// l (unsigned form of i) is 4294967292
		System.out.println("as unsigned int: " + l);
		System.out.println("Binary str (long): " + Long.toBinaryString(l));
		
		// 62 bit arithmetic ------------------------------
	}
}
