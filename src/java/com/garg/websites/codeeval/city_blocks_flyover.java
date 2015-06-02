package com.garg.websites.codeeval;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * https://www.codeeval.com/open_challenges/133/
 */
public class city_blocks_flyover
{
	private static final float EPSILON = 0.001f;

	public static void main(String[] args) throws IOException
	{
		String file =  args[0];
		BufferedReader fr = null;
		try {
			fr = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = fr.readLine()) != null) {
				// (0,1,3,4,6) (0,1,2,4)
				// (streets - x) (avenues - y)
				String[] input = line.split(" ");
				String[] xs = input[0].substring(1, input[0].length() - 1).split(",");
				String[] ys = input[1].substring(1, input[1].length() - 1).split(",");
				int count = getBlockCount(xs, ys); 
				System.out.println(count);
			}
		}
		finally {
			if (fr != null) {
				fr.close();
			}
		}
	}

	private static int getBlockCount(String[] xs, String[] ys) 
	{
		float slope = (1.0f * toInt(ys[ys.length - 1])) / toInt(xs[xs.length - 1]);
		// Iterate over the x axis (street).
		// Consider each street block by block..
		// .. and keep a count of how many avenues it passed by in each street.
		// This can be done by finding entry and exit points (y intersects) 
		// for each street and finding how many avenues it lies on.
		int iy = 0, ix = 0; // where we reached on y axis. 
		int count = 0;
		for (ix = 1; ix < xs.length; ix++) {
			int x = toInt(xs[ix]);
			float y = slope * x;
			count++;
			while (GT(y, toInt(ys[iy+1]))) {
				iy++;
				count++;
			}
			if (toInt(ys[iy+1]) == y) iy++;
		}
		return count;
	}

	/**
	 * Returns true if a > b.
	 */
	private static boolean GT(float a, float b) 
	{
		return (a - b > EPSILON);
	}

	private static int toInt(String string) 
	{
		return Integer.parseInt(string);
	}
}
