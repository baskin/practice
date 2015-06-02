package com.garg.dice;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

public class DefaultStrategy implements Strategy
{
    private static final int MEMORY_LENGTH = 10;
    private final Random rnd = new Random(System.currentTimeMillis() + 999999);
    private float vault = 0.0f;
    
    private float nextBet = 0.0f;
    private int nextPrediction = -1;
    
    private final Deque<Integer> resultChain = new LinkedList<Integer>();
    int lowCounter = 0;
    
    
    @Override
    public void seedMoney(float seed) {
        vault += seed;
        nextBet = 0.0f;
    }
    
    @Override
    public void feedResult(int actual, float reward, boolean won)
    {
        vault += reward;
        assert vault > 0;
        
        resultChain.addLast(actual);
        if (actual < 50) {
            lowCounter++;
        }
        if (resultChain.size() == MEMORY_LENGTH + 1) {
            Integer removedResult = resultChain.removeFirst();
            if (removedResult < 50) {
                lowCounter--;
            }
        }
    }
    
    @Override
    public void predict()
    {
        System.out.println(resultChain + ", lows->" + lowCounter);
        
        nextBet = 0.0f; // vault/10;
        nextPrediction = generateRandom();
        
        if (resultChain.size() < MEMORY_LENGTH) {
            return;
        }
        
        if (lowCounter == MEMORY_LENGTH - 1 || lowCounter == 1) {
            System.out.println("*******");
            nextBet = vault * 0.1f;
        }
        else if (lowCounter == MEMORY_LENGTH || lowCounter == 0) {
            System.out.println("******************");
            nextBet = vault * 0.25f ;
        }
        if (lowCounter > MEMORY_LENGTH - 2) {
            nextPrediction = 51 + rnd.nextInt(49);
        }
        else if (lowCounter < 2) {
            nextPrediction = rnd.nextInt(50);
        }
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
        assert nextBet < vault;
        return nextBet;
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
