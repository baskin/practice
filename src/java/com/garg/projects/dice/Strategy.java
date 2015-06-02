package com.garg.projects.dice;

public interface Strategy
{
    public void seedMoney(float seed);
    
    public void predict();

    public int nextPrediction();

    public float nextBet();
    
    public void feedResult(int actual, float reward, boolean won);
    
    public float getVaultValue();
}