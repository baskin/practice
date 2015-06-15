package com.garg.websites.gfg.stack;

import java.util.Stack;

/**
 * http://geeksquiz.com/stack-set-2-infix-to-postfix/
 */ 
public class ExpressionEvaluation
{
    private static final String PRECEDENCE_ORDER = "-+*/";
    
    public static void main(String[] args)
    {
        System.out.println(in2post("1+2*3-4/6".toCharArray()));
        System.out.println(eval(in2post("1+2*3-4/6".toCharArray())));
        System.out.println(in2post("1+2*3-4".toCharArray()));
        System.out.println(eval(in2post("1+2*3-4".toCharArray())));
    }
    
    public static char[] in2post(char[] infix) 
    {
        char[] postfix = new char[infix.length];
        Stack<Character> opStack = new Stack<Character>();
        
        int j = 0;
        for (int i = 0; i < infix.length; i++) {
            char c = infix[i];
            if (isOperand(c)) {
                postfix[j] = c;
                j++;
            }
            else if (isOperator(c)) {
                // +, * followed by -
                while (!opStack.isEmpty() && precedence(c) < precedence(opStack.peek())) {
                    postfix[j] = opStack.pop();
                    j++;
                }
                // Now you have emoty opStack or stack with top operator having lower
                // precedence than current
                opStack.push(c);
            }
            else {
                throw new IllegalArgumentException();
            }
        }
        while (! opStack.isEmpty()) {
            postfix[j] = opStack.pop();
            j++;
        }
        return postfix;
    }
    
    public static int eval(char[] postfix)
    {
        Stack<Integer> operands = new Stack<Integer>();
        for (char c : postfix) {
            if (isOperand(c)) {
                operands.push(c - '0');
            }
            else if (isOperator(c)) {
                operands.push(eval(c, operands.pop(), operands.pop()));
            }
            else {
                throw new IllegalArgumentException();
            }
        }
        return operands.pop();
    }

    private static int eval(char c, int r, int l)
    {
        if (c == '-') {
            return l-r;
        }
        if (c == '+') {
            return l+r;
        }
        if (c == '*') {
            return l*r;
        }
        if (c == '/') {
            return l/r;
        }
        throw new IllegalArgumentException();
    }

    private static int precedence(char c)
    {
        return PRECEDENCE_ORDER.indexOf(c);
    }

    private static boolean isOperator(char c)
    {
        return PRECEDENCE_ORDER.indexOf(c) >= 0;
    }

    private static boolean isOperand(char c)
    {
        return c >= '0' && c <= '9';
    }
    
}