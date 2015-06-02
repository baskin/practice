package com.garg.concepts.algos;

public class Factors
{
	/**
	 * Print all (prime) factors of x. 
	 */
	private static String factorize(int x)
	{
		StringBuilder sb = new StringBuilder();
		int factor = 2, lastfactor = 2;
		int factorCount = 0;
		while (x > 1) {
			if (x % factor == 0) {
				if (lastfactor != factor && factorCount != 0) {
					// New factor.
					sb.append(lastfactor + "(" + factorCount + ") ");
					factorCount = 0;
				}
				factorCount++;
				lastfactor = factor;
				x = x/factor;
			}
			else {
				factor += (factor == 2) ? 1 : 2;
			}
		}
		sb.append(lastfactor + "(" + factorCount + ") ");
		return sb.toString();
	}
	

	/**
	 * GCD(a, b) = c ---> i.e. the largest number that can divide 
                        both a and b, is the GCD c. 
	 */
	private static int gcd(int a, int b)
	{
		// Let a be larger of the two.
		if (a < b) {
			int temp = a;
			a = b;
			b = temp;
		}
		
		int factor = 2;
		int gcd = 1;
		while (factor <= b) {
			if (a % factor == 0 && b % factor == 0) {
				a /= factor;
				b /= factor;
				gcd *= factor;
			}
			else {
				factor += (factor == 2) ? 1 : 2;
			}
		}
		return gcd;
	}
	
	/**
	 *
	 * The Euclid algorithm states that, 
	 * all you have to do to find the gcd is,
	 * 1. Divide the larger number with the smaller and get the remainder.
	 * 2. Now find the GCD of the smaller number and this remainder.
	 * 3. If the remainder is zero, then the smaller number is GCD.
	 */
	private static int euclidGcd(int a, int b) 
	{
		// Let a be larger of the two.
		if (a < b) {
			int temp = a;
			a = b;
			b = temp;
		}
		while (b > 0) {
			int r = a % b;
			a = b;
			b = r;
		}
		return a;
	}
	
	public static void main(String[] args)
	{
		for (int x : new int[] {300, 129, 32, 91, 19304595, 803409830}) {
			System.out.println("Factorization of " + x + ":" + factorize(x));
		}
		
		System.out.println(gcd(17, 51));
		System.out.println(euclidGcd(17, 51));
	}

}
