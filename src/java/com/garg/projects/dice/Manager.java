package com.garg.projects.dice;

public class Manager
{
    private volatile boolean shouldStop = false;
    private int runs = 0;
    
    public void run(float seedMoney, float target, Game g, Strategy s) throws Exception {
        s.seedMoney(seedMoney);
        System.out.println("Starting vault:" + s.getVaultValue());
        int total = 0, wins = 0;
        float earnt = 0.0f, lost = 0.0f;
        float maxVault = 0.0f, minVault = seedMoney; 
        while (!shouldStop) {
            runs++;
            System.out.println("---------------------------");
            int actual = g.nextValue();
            total++;
            
            s.predict();
            int prediction = s.nextPrediction();
            float bet = s.nextBet();
            
            if (bet > s.getVaultValue() || s.getVaultValue() < 0.000001f) {
                throw new Exception("Out of money!");
            }
            
            System.out.println(String.format("Bet amount: %f", bet));
            System.out.println(String.format("Prediction: %d\nActual: %d", prediction, actual));
            boolean isWin = g.reward(actual, prediction);
            float reward = isWin ? bet : -bet;
            if (isWin) {
                wins++;
                earnt += reward;
            }
            else {
                lost -= reward;
            }
            
            s.feedResult(actual, reward, isWin);
            System.out.println(String.format("Reward amount: %f", reward));
            System.out.println(String.format("Vault value: %f", s.getVaultValue()));
            System.out.println(String.format("win/loss: %d/%d", wins, total - wins));
            System.out.println(String.format("earnt/lost: %f/%f", earnt, lost));
            
            if (s.getVaultValue() > maxVault) {
                maxVault = s.getVaultValue();
            }
            else if (s.getVaultValue() < minVault) {
                minVault = s.getVaultValue();
            }
            System.out.println(String.format("max/min vault: %f/%f", maxVault, minVault));
            
            if (s.getVaultValue() > target) {
                return;
            }
            
//            Thread.sleep(100);
        }
    }
    
    public static void main(String[] args)
    {
        Game g = new FairGame();
        Strategy s = new Martingale(1.0f);
        Manager m = new Manager();
        float seedMoney = 100.0f;
        float target = 1.5f * seedMoney;
        int steps = 5;
        float stepTarget = (target - seedMoney)/steps;
        float money = seedMoney;
        try {
            while (money < target) {
                System.out.println(String.format("\n\nRunning with seed: %f and target: %f", money, money + stepTarget));
                m.run(money, money + stepTarget, g, s);
                money += stepTarget;
                System.out.println("Step target achieved! Vault:" + money + ". Dices thrown:" + m.runs);
                Thread.sleep(100);
            }
            System.out.println("Final target achieved!!");
        }
        catch (Exception e) {
            System.out.println("Stopping.." + e.getMessage());
        }
    }
}
