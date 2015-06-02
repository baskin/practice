package com.garg.websites.codeeval;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * http://www.codeeval.com/open_challenges/1/
 */
public class minesweeper
{
	public static void main(String[] args) throws IOException
	{
		String file =  args[0];
		BufferedReader fr = null;
		try {
			fr = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = fr.readLine()) != null) {
				// 3,5;**.........*...
				String[] input = line.split(";");
				String[] dims = input[0].split(",");
				int[][] field = 
					buildField(
						Integer.parseInt(dims[0]), Integer.parseInt(dims[1]), input[1]); 
				printField(field);
				System.out.println();
			}
		}
		finally {
			if (fr != null) {
				fr.close();
			}
		}
	}

	private static void printField(int[][] field) 
	{
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[0].length; j++) {
				// -1 for mine.
				int d = field[i][j];
				System.out.print((d == -1) ? "*" : d);
			}
		}
	}

	private static int[][] buildField(int row, int col, String data) 
	{
		int [][]field = new int[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				// 0 if empty,
				// -1 for mine.
				char c = data.charAt(i*col + j);
				field[i][j] = c == '*' ? -1 : 0;
			}
		}
		
		// Now iterate and spot mines.
		// For each mine found, go over the 8 possible locs and increment neighbor count.
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (field[i][j] == -1) {
					// Its a mine!
					update(field, i, j, -1,  0); 
					update(field, i, j, -1, -1);
					update(field, i, j,  0, -1); 
					update(field, i, j,  1, -1); 
					update(field, i, j,  1,  0); 
					update(field, i, j,  1,  1); 
					update(field, i, j,  0,  1); 
					update(field, i, j, -1,  1); 
				}
			}
		}
		return field;
	}

	private static void update(int[][] field, int x, int y, int xInc, int yInc) 
	{
		x += xInc;
		y += yInc;
		if (x >= 0 && x < field.length && y >= 0 && y < field[0].length) {
			if (field[x][y] != -1) {
				// inc if not a mine.
				field[x][y] += 1;
			}
		}
	}
}
