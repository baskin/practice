package com.garg.websites.codeeval;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * https://www.codeeval.com/open_challenges/78/
 */
public class valid_sudoku {
	public static void main(String[] args) {
		String file = args[0];
		BufferedReader fr = null;
		try {
			fr = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = fr.readLine()) != null) {
				String[] t = line.split(",");
				System.out.println(doOverlap(Integer.parseInt(t[0]),
						Integer.parseInt(t[1]), Integer.parseInt(t[2]),
						Integer.parseInt(t[3]), Integer.parseInt(t[4]),
						Integer.parseInt(t[5]), Integer.parseInt(t[6]),
						Integer.parseInt(t[7])) ? "True" : "False");
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	private static boolean doOverlap(
			// 1 -> top left, 2 bottom right
		int ax1, int ay1, int ax2, int ay2, 
		int bx1, int by1, int bx2, int by2) 
	{
		if (bx1 > ax2 || ax1 > bx2) {
			// one lies completely to right of another
			return false;
		}
		if (ay1 < by2 || by1 < ay2) {
			return false;
		}
		return true	;
	}
}
