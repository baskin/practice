/*
 * ExchangerExample.java
 *
 * $Header: /proj/infra/repository/desmake/etc/deshaw_jalopy.xml,v 1.4 2007/09/12 10:24:53 lalam Exp $
 */

/*
 * Copyright (c) 2008 D. E. Shaw & Co., L.P. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of D. E. Shaw & Co., L.P. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with D. E. Shaw & Co., L.P.
 */

package com.garg.concepts.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Exchanger;

/**
 * A class that uses an Exchanger to swap buffers between threads so that the
 * thread filling the buffer gets a freshly emptied one when it needs it,
 * handing off the filled one to the thread emptying the buffer.
 *
 * @author gargb
 *
 */
public class ExchangerExample
{	
    private static final int FULL = 10;
    
    private static final int COUNT = FULL * 20;
    
    private static final Random random = new Random();
    
    private static volatile int sum = 0;
    
    private static Exchanger<List<Integer>> exchanger =
        new Exchanger<List<Integer>>();
    
    private static List<Integer> initiallyEmptyBuffer;
    
    private static List<Integer> initiallyFullBuffer;
    
    private static CountDownLatch stopLatch = new CountDownLatch(2);

    private static class FillingLoop implements Runnable
    {
        private final CountDownLatch startSignal;

		public FillingLoop(CountDownLatch startSignal) {
			this.startSignal = startSignal;
		}

		public void run()
        {
			try {
				startSignal.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            List<Integer> currentBuffer = initiallyEmptyBuffer;

            try {
                for (int i = 0; i < COUNT; i++) {
                    if (currentBuffer == null) {
                        break; // stop on null
                    }

                    Integer item = random.nextInt(100);
                    System.out.println("Added: " + item);
                    currentBuffer.add(item);

                    if (currentBuffer.size() == FULL) {
                        currentBuffer = exchanger.exchange(currentBuffer);
                        System.out.println(Thread.currentThread().getName() + ": " + "Exchanged");
                    }
                }
            }
            catch (InterruptedException ex) {
                System.out.println("Bad exchange on filling side");
            }

            stopLatch.countDown();
        }
    }

    private static class EmptyingLoop implements Runnable
    {
        private final CountDownLatch startSignal;

		public EmptyingLoop(CountDownLatch startSignal) {
			this.startSignal = startSignal;
		}

		public void run()
        {
			try {
				startSignal.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            List<Integer> currentBuffer = initiallyFullBuffer;

            try {
                for (int i = 0; i < COUNT; i++) {
                    if (currentBuffer == null) {
                        break; // stop on null
                    }

                    Integer item = currentBuffer.remove(0);
                    System.out.println("Got: " + item);
                    sum += item.intValue();

                    if (currentBuffer.isEmpty()) {
                        currentBuffer = exchanger.exchange(currentBuffer);
                        System.out.println(Thread.currentThread().getName() + ": " + "Exchanged");
                    }
                }
            }
            catch (InterruptedException ex) {
                System.out.println("Bad exchange on emptying side");
            }

            stopLatch.countDown();
        }
    }

    public static void main(String[] args)
    {
        initiallyEmptyBuffer = new ArrayList<Integer>();
        initiallyFullBuffer = new ArrayList<Integer>(FULL);

        for (int i = 0; i < FULL; i++) {
            initiallyFullBuffer.add(random.nextInt(100));
        }
        CountDownLatch startSignal = new CountDownLatch(1);
        new Thread(new FillingLoop(startSignal), "Producer").start();
        new Thread(new EmptyingLoop(startSignal), "Consumer").start();
        startSignal.countDown();

        try {
            stopLatch.await();
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        System.out.println("Sum of all items is.... " + sum);
    }

}
