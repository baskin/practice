package com.garg.concepts.algos;

import java.util.HashMap;
import java.util.Map;

public class DynamicProgramming
{
	private static class Knapsack
	{
		private int capacity;
		private int[] profit;
		private int[] weight;
		private int[] solution;
		private int n;
		
		private static class Arg {
			private int arg1;
			private int arg2;
			
			public Arg(int i, int y)
			{
				arg1 = i;
				arg2 = y;
			}

			@Override
			public boolean equals(Object obj)
			{
				return ((Arg) obj).arg1 == this.arg1
				    && ((Arg) obj).arg2 == this.arg2;
			}
			
			@Override
			public int hashCode()
			{
				return  31 *arg1 + arg2;
			}
		}
		private Map<Arg, Integer> memoizedProfit = 
			new HashMap<Arg, Integer>();

		public Knapsack(int capacity, int[] profit, int[] weight, int n)
		{
			this.capacity = capacity;
			this.profit = profit;
			this.weight = weight;
			this.n = n;
			solution = new int[weight.length];
		}
		
		public void solve()
		{
			int maxProfit = memoizedKnap(1, capacity);
			
			StringBuilder sb = new StringBuilder("MaxProfit: " + maxProfit + "  solution: { ");
			for (int i = 1; i < weight.length; i++) {
				if (solution[i] == 1) {
					sb.append(weight[i] + "," + profit[i] + " ");
				}
			}
			sb.append("}");
			System.out.println(sb.toString());
		}

		private int memoizedKnap(int i, int y)
		{
			Arg arg = new Arg(i, y);
			Integer profit = memoizedProfit.get(arg);
			if (profit == null) {
				profit = knap(i, y);
				memoizedProfit.put(arg, profit);
			}
			return profit;
		}

		private int knap(int i, int y)
		{
			if (i == n) {
				if (weight[n] > y) {
					solution[n] = 0;
					return 0;
				}
				else {
					solution[n] = 1;
					return profit[n];
				}
			}

			if (weight[i] > y) {
				solution[i] = 0;
				return memoizedKnap(i + 1, y);
			}
			int excl = memoizedKnap(i + 1, y);
			int incl = memoizedKnap(i + 1, y - weight[i]) + profit[i];
			if (incl > excl) {
				solution[i] = 1;
				return incl;
			}
			else {
				solution[i] = 0;
				return excl;
			}
		}
	}

	public static void main(String[] args)
	{
		int[] weight = { 0,  4, 13, 6, 2, 9 };
		int[] profit = { 0, 10, 12, 3, 5, 7 };
		Knapsack k = new Knapsack(20, profit, weight, 5);
		k.solve();
	}

}
