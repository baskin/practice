package com.garg.websites.gfg.stack;

import java.util.Stack;

/**
 * Reverse a stack inplace.
 * 
 * @author bgarg
 */
public class StackReversal
{
    public static void main(String[] args)
    {
        Stack<Integer> s = new Stack<Integer>();
        s.add(1);
        s.add(2);
        s.add(3);
        s.add(4);
        s.add(5);
        
        reverse(s);
        
        while (!s.isEmpty()) {
            System.out.print(s.pop() + " ");
        }
    }

    /**
     * e.g.   1 2 3 4
     * we start from top.
     * Assume we're recursing over 4
     * we pop 4 and reverse the remaining stack.
     * we get 3 2 1 4
     * now we need to insert 4 at the bottom.
     * we get 4 3 2 1
     */
    private static void reverse(Stack<Integer> s)
    {
        if (s.isEmpty()) {
            return;
        }
        Integer popped = s.pop();
        reverse(s);
        insertBottom(s, popped);
    }

    /**
     * Insert e at bottom of stack using recursion. 
     */
    private static void insertBottom(Stack<Integer> s, int e)
    {
        if (s.isEmpty()) {
            s.push(e);
        }
        else {
            Integer popped = s.pop();
            insertBottom(s, e);
            s.push(popped);
        }
    }
}
