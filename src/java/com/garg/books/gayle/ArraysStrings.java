package com.garg.books.gayle;

public class ArraysStrings
{
	public static boolean hasUniqueChar(String s)
	{
		if (s == null) {
			return true;
		}

		int flag = 0;
		for (int i = 0; i < s.length(); i++) {
			int val = s.charAt(i) - 'a';
			if ((flag & (1 << val)) > 0) {
				return false;
			}
			flag |= 1 << val;
		}
		return true;
	}
	
	/**
	 * Given sorted rotated array. Find smallest element index.
	 */
	public static int sortedRotatedArray(int [] array)
	{
		if (array.length < 2) {return array.length - 1;}
		return binaryLocate(array, 0, array.length - 1);
	}
	
	private static int binaryLocate(int []a, int l, int h)
	{
		if (h - l == 1) {
			return a[l] <= a[h] ? l : h; 
		}
		if ( l == h) {
			return l;
		}
		
		int mid = (l+h)/2;
		if (a[l] == a[mid]) {
			int rr = binaryLocate(a, mid + 1, h);
			int ll = binaryLocate(a, l, mid);
			return a[ll] <= a[rr] ? ll : rr;
		}			
		else if (a[l] > a[mid]) {
			return binaryLocate(a, mid + 1, h);
		}
		else {
			return binaryLocate(a, l, mid);
		}
	}
	
	private static void printAllSubsets(int []a)
	{
		int count = 0;
		for (int i = 0; i < 1 << a.length; i++) {
			System.out.print("(");
			boolean first = true;
			for (int j = 0; j < a.length; j++) {
				if ( (i & (1 << j)) > 0) {
					if (!first) {
						System.out.print(" ");
					}
					first = false;
					System.out.print(a[j]);
				}
			}
			System.out.println(")");
			count++;
		}
		System.out.println("Total " + count + " sets.");
	}
	
	public static int removeSortedDuplicates(char []str)
	{
		if (str == null) {
			return -1;
		}
		if (str.length < 2) {
			return str.length;
		}
		
		int j = 0;
		for (int i = 1; i < str.length; i++) {
			if (str[i] != str[i-1]) {
				str[j] = str[i-1];
				j++;
			}
		}
		str[j] = str[str.length - 1];
		return ++j;
	}
	
	public static int remoteDuplicates(char []str)
	{
		
		return -1;
	}

	public static void main(String[] args)
	{
//		System.out.println("hasUniqueChar(bhupis):" + hasUniqueChar("bhupibs"));
		
//		int array[] = {8,9,1,2,3,4,5,6,7};
//		int array1[] = {8,9,10,12,13,14,15,6,7};
//		System.out.println(sortedRotatedArray(array));
		
//		int a[] = {1,2,3, 4, 5};
//		printAllSubsets(a);
		
		char []s = "abbbbbccccddddde".toCharArray();
		int len = removeSortedDuplicates(s);
		System.out.println(String.valueOf(s, 0, len));
	}
}
