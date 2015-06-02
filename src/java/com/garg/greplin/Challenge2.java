package com.garg.greplin;

/**
 * http://challenge.greplin.com/
 */
public class Challenge2 {
	/**
	 * To get the password for level 3, write code to find the first prime
	 * fibonacci number larger than a given minimum. For example, the first
	 * prime fibonacci number larger than 10 is 13.
	 * 
	 * When you are ready, go here or call this automated number (415) 799-9454.
	 * 
	 * You will receive additional instructions at that time. For the second
	 * portion of this task, note that for the number 12 we consider the sum of
	 * the prime divisors to be 2 + 3 = 5. We do not include 2 twice even though
	 * it divides 12 twice.
	 * 
	 */
	public static int fiboGreaterThan(int n)
	{
		// fib[0] = 1, fib[1] = 1;
		int a = 1, b = 1;
		while (a < n || !isPrime(a)) {
			int nextFibo = a + b;
			b = a;
			a = nextFibo;
		}
		return a;
	}

	private static boolean isPrime(int a) 
	{
		if (a == 2) {
			return true;
		}
		int factor = 2;
		while (factor < a/2) {
			if (a % factor == 0) {
				return false;
			}
			factor += (factor == 2 ? 1 : 2);
		}
		return true;
	}
	
	public static int primeDivisorSum(int a)
	{
		int sum = 0;
		int factor = 2, lastFactor = -1;
		while (a > 1) {
			if (a % factor == 0) {
				if (lastFactor != factor) {
					sum += factor;
				}
				lastFactor = factor;
				a = a/factor;
			}
			else {
				factor += (factor == 2 ? 1 : 2);
			}
		}
		return sum;
	}

	public static void main(String[] args) {
		int f = fiboGreaterThan(227000);
		System.out.println(f + " " + primeDivisorSum(f + 1));
	}
}
