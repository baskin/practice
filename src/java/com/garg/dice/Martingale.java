package com.garg.dice;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

/**
 * It works by doubling the amount of your wager every time you lose. With this,
 * by the time you win; will gain all the wagers you have lost in your previous
 * games.
 * 
 * @author bgarg
 */
public class Martingale implements Strategy
{
    private static final int MEMORY_LENGTH = 1;
    private final float seedBet;

    private final Random rnd = new Random(System.currentTimeMillis() + 999999);

    private float vault = 0.0f;
    private float maxVault = 0.0f;
    private float seed = 0.0f;

    private int nextPrediction = -1;
    private float nextBet = 0.0f;

    private final Deque<Integer> resultChain = new LinkedList<Integer>();
    int lowCounter = 0;
    
    private int maxMultiplier = 1;

    public Martingale(float sb)
    {
        seedBet = sb;
    }

    @Override
    public void seedMoney(float s)
    {
        seed = s;
        vault = seed;
    }

    @Override
    public void feedResult(int actual, float reward, boolean won)
    {
        vault += reward;
        if (vault > maxVault) {
            maxVault = vault;
        }
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

        if (won) {
            if (((lowCounter == MEMORY_LENGTH || lowCounter == 0)) && maxMultiplier > 3) {
                // if you've won already, but still the streak continues
                // better off betting higher than 0.0.
                nextBet = 0.5f * nextBet;
            }
            else {
                nextBet = 0.0f;
            }
            maxMultiplier = 0;
            return;
        }

        // Just lost :(
        // Single continuous streak.
        if (lowCounter == MEMORY_LENGTH || lowCounter == 0) {
            if (vault < seed/2) {
                // vault is already taking losses, don't gamble hard!
                nextBet = seedBet;
            }
            else {
                nextBet = Math.max(2 * nextBet, seedBet);
            }
            maxMultiplier++;
            
            if (nextBet > vault/2) {
                System.out.println("Taking loss in martingale!");
                try {
                    Thread.sleep(100);
                }
                catch (InterruptedException e) {
                }
                // take loss and reset
                nextBet = 0.0f;
                maxMultiplier = 0;
            }
        }
    }

    @Override
    public void predict()
    {
        System.out.println(resultChain + ", lows->" + lowCounter + ", multiplier->" + maxMultiplier);
        
        if (vault < maxVault /3) {
            throw new RuntimeException("Stop loss activated");
        }
        
        if (lowCounter == MEMORY_LENGTH) {
            nextPrediction = 51 + rnd.nextInt(49);
        }
        else if (lowCounter == 0) {
            nextPrediction = rnd.nextInt(50);
        }
        else {
            nextPrediction = generateRandom();
        }
    }

    @Override
    public int nextPrediction()
    {
        assert nextPrediction != -1;
        int returnValue = nextPrediction;
        nextPrediction = -1;
        return returnValue;
    }

    @Override
    public float nextBet()
    {
        return nextBet;
    }

    /**
     * Generate random number 1-99, excluding 50.
     */
    private int generateRandom()
    {
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
