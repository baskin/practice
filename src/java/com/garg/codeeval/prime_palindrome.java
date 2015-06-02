package com.garg.codeeval;

/**
 * http://www.codeeval.com/open_challenges/1/
 */
public class prime_palindrome
{
	public static void main(String[] args)
	{
		 int sum = 0;
		 for (int i = 2, n = 0; n < 1000; i++) {
			 if (isPrime(i)) {
				 sum += i;
				 n++;
			 }
		 }
		 System.out.println(sum);
	}

	private static boolean isPrime(int i)
	{
		int factor = 2;
		while (factor <= i/2) {
			if (i % factor == 0) {
				return false;
			}
			factor += factor == 2 ? 1 : 2;
		}
		return true;
	}

	private static boolean isPalindrome(int i)
	{
		int n = i, nn = 0;
		// 2849
		while (n > 0) {
			nn = nn*10 + n%10;
			n /= 10;
		}
		return i == nn;
	}
}
