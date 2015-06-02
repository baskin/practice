package com.garg.concepts.algos;

import java.util.Stack;
import java.util.Comparator;

public class SortUtil 
{	
	public enum SortType {
		INSERTION,
		RANK,
		MERGE,
		QUICK,
		TAIL_QUICK,
	};
	
	public static void sort(int[] a, SortType type)
	{
		switch (type) {
		case RANK:
			rankSort(a);
			break;
		case MERGE:
			mergeSort(a);
			break;
		case QUICK:
			quickSort(a, null);
			break;
		case TAIL_QUICK:
			tailRecursiveQuickSort(a);
			break;
		case INSERTION:
			insertionSort(a);
		default:
			break;
		}
	}
	
	public static void insertionSort(int[] a)
	{
		for (int i = 0; i < a.length; i++) {
			for (int j = i-1; j >= 0; j--) {
				if (a[j] > a[j+1]) {
					Util.swap(a, j, j+1);
				}
				else {
					break;
				}
			}
		}
	}
	
	/** 
	 * Sort the given array by making use of a ranked array.
	 * c[rank[i]] = a[i], will yield c[] --> a sorted a[]. 
	 */
	public static void rankSort(int[] a)
	{
		int[] rank = Util.rank(a);
		for (int i = 0; i < a.length; i++) {
			while(i != rank[i]) {
				Util.swap(a,    i, rank[i]);
				Util.swap(rank, i, rank[i]);
			}
		}
	}
	
	/** Sort a[]*/
	public static void mergeSort(int[] a)
	{
		int[] temp = new int[a.length];
		mergeSort(a, temp, 0, a.length -1);
	}
	
	/** Sort a[l:h], l and h both inclusive. */
	private static void mergeSort(int[] a, int[] temp, int l, int h)
	{
		if (l >= h) {
			return;
		}
		int m = (l + h)/2; // l = (h-l)/2
		mergeSort(a, temp, l, m);
		mergeSort(a, temp, m + 1, h);
		Util.merge(a, temp, l, m, h);
	}
	
	/** Sort a[] */
	public static void quickSort(int[] a, Comparator<Integer> cmp)
	{
		quickSort(a, 0, a.length - 1, cmp);
	}
	
	/** sort a[l:h], l and h both inclusive. */
	private static void quickSort(int[] a, int l, int h,  Comparator<Integer> cmp)
	{
		if (l >= h) {
			return;
		}
		int pivot = Util.randomPivot(a, l, h, cmp);
		quickSort(a, l, pivot - 1, cmp);
		quickSort(a, pivot + 1, h, cmp);
	}
	
	public static void tailRecursiveQuickSort(int[] a)
	{
		tailRecursiveQuickSort(a, 0, a.length - 1);
	}
	
	/** 
	 * Sort a[l:h], l and h both inclusive, use a tail recursive approach to 
	 * reduce space complexity. 
	 */
	private static void tailRecursiveQuickSort(int[] a, int l, int h)
	{
		while (l < h) {
			int pivot = Util.pivot(a, l, h);
			quickSort(a, l, pivot - 1, null);
			l = pivot + 1;
		}
	}
	
	public static void iterativeQuickSort(int[] a)
	{
		class Arg {
			public Arg(int low, int high)
			{
				l = low;
				h = high;
			}
			int l; int h;
		}
		Stack<Arg> stack = new Stack<Arg>();
		stack.push(new Arg(0, a.length - 1));
		while (!stack.isEmpty()) {
			Arg pop = stack.pop();
			int l = pop.l;
			int h = pop.h;
			if (l < h) {
				int pivot = Util.pivot(a, l, h);
			    stack.push(new Arg(l, pivot - 1));
//			    l = pivot + 1; // uncomment for version created from tail recursive 
			    stack.push(new Arg(pivot + 1, h));
			}
		}
	}
	
	public static void main(String[] args) 
	{
		int[] a = {3 ,45, -2, 6, 90};
		int[] b = {3 ,45, -2, 6, 90, 90, 10};
		int[] c = {1, 1, 1, 1, 1, 1, 1};
		int[] d = {};
		int[] e = {-34};
		int[] f = {2, 4, 7, 45, 78, 135, 345};
		
		for (int[] array : new int[][]{a, b, c, d, e, f}) {
			System.out.printf("%10s :", "UNSORTED");
			Util.print(array);
			
			for (SortType t : SortType.values()) {
				sort(array, t);
				System.out.printf("%10s :", t.toString());
				Util.print(array);
			}
			System.out.println();
		}
	}
}
