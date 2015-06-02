package com.garg.codeeval;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

/**
 * http://www.codeeval.com/open_challenges/1/
 */
public class prefix
{
	public static void main(String[] args) throws IOException
	{
		BufferedReader fr = null;
		try {
			fr = new BufferedReader(new InputStreamReader(System.in));
			String line = null;
			while ((line = fr.readLine()) != null) {
				String[] input = line.split(" ");
				evaluatePrefix(input);
			}
		}
		finally {
			if (fr != null) {
				fr.close();
			}
		}
	}

	private static void evaluatePrefix(String[] input)
	{
		Stack<String> stack = new Stack<String>();
		// x + 2 3 + 4 5 
		// x 5 + 4 5
		// x 5 9
		for (String s : input) {
			while (!stack.isEmpty() && isOperand(s) && isOperand(stack.peek())) {
				s = evaluate(stack.pop(), s, stack.pop());
			}
			stack.push(s);
		}
		System.out.println(stack.pop());
	}

	private static String evaluate(String operand1, String operand2, String operator)
	{
		int o1 = Integer.parseInt(operand1);
		int o2 = Integer.parseInt(operand2);
		if (operator.equals("+")) {
			return String.valueOf(o1 + o2);
		}
		if (operator.equals("x") || operator.equals("*")) {
			return String.valueOf(o1 * o2);
		}
		if (operator.equals("-")) {
			return String.valueOf(o1 - o2);
		}
		if (operator.equals("/")) {
			return String.valueOf(o1 / o2);
		}
		throw new IllegalArgumentException("Invalid operator " + operator);
	}

	private static boolean isOperand(String peek)
	{
		try {
			Integer.parseInt(peek);
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}
}