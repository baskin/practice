package com.garg.dice;

import java.util.Random;

public class RandomStrategy implements Strategy
{
    private final Random rnd = new Random(System.currentTimeMillis() + 999999);
    
    private float vault = 0.0f;

    private int nextPrediction = -1;
    
    @Override
    public void seedMoney(float seed) {
        vault = seed;
    }
    
    @Override
    public void feedResult(int actual, float reward, boolean won)
    {
        vault += reward;
        assert vault > 0;
    }
    
    @Override
    public void predict() {
        nextPrediction = generateRandom();
    }
    
    @Override
    public int nextPrediction() {
        assert nextPrediction != -1;
        int returnValue = nextPrediction;
        nextPrediction = -1;
        return returnValue;
    }
    
    @Override
    public float nextBet() {
        // limit of vault
        return 0.0001f;
    }
    
    /**
     * Generate random number 1-99, excluding 50.
     * @return
     */
    private int generateRandom() {
        int result = rnd.nextInt(100);
        while (result == 50) {
            result = rnd.nextInt(100);
        }
        return result;
    }

    @Override
    public float getVaultValue()
    {
        return vault;
    }
}
