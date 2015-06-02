package com.garg.codeeval;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * http://www.codeeval.com/open_challenges/1/
 */
public class largest_sum
{
	public static void main(String[] args) throws IOException
	{
		String file =  args[0];
		BufferedReader fr = null;
		try {
			fr = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = fr.readLine()) != null) {
				if (!line.trim().equals("")) {
					String[] input = line.split(",");
					int[] array = new int[input.length];
					for (int i = 0; i < input.length; i++) {
						array[i] = Integer.parseInt(input[i].trim());
					}
					System.out.print(largestSum(array));
				}
				System.out.println();
			}
		}
		finally {
			if (fr != null) {
				fr.close();
			}
		}
	}

	// -10, 2, 3, -2, 0, 5, -15
	// 2,3,-2,-1,10
	private static int largestSum(int[] array)
	{
		if (array.length == 0) {
			return 0;
		}
		int s = array[0], bs = array[0];
		for (int i = 1; i < array.length; i++) {
			bs = Math.max(bs + array[i], array[i]);
			s  = Math.max(s, bs);
		}
		return s;
	}
}
