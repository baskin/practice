package com.garg.projects.dice;

import java.util.Random;

public class FairGame implements Game
{
    private final Random rnd = new Random(System.currentTimeMillis());
    
    @Override
    public int nextValue() {
        return rnd.nextInt(100);
    }

    @Override
    public boolean reward(int actual, int predicted)
    {
        // 0  -> -50
        // 3  -> -47
        // 49 -> -1
        // 50 -> 0
        // 51 -> 1
        // 99 -> 49
        assert actual    >= 0 && actual < 100;
        assert predicted >= 0 && predicted < 100 && predicted != 50;
        actual -= 50;
        predicted -= 50;
        return ((actual < 0 && predicted < 0) || (actual > 0 && predicted > 0));
    }
}
