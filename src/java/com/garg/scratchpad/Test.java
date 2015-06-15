
package com.garg.scratchpad;

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
