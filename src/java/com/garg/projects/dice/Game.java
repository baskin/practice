package com.garg.projects.dice;

public interface Game
{
    public int nextValue();
    
    public boolean reward(int actual, int predicted);
}