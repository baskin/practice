/*
 * SynchronousQueueTest.java
 *
 * $Header$#
 */

package com.garg.concepts.concurrency;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;

/**
 * An example program for testing {@link SynchronousQueue}.
 *
 * @author gargb
 */
public class SynchronousQueueTest
{
    /**
     * Consumer.
     */
    private static class Consumer extends Thread
    {
        private final SynchronousQueue<Task> queue;

        public Consumer(SynchronousQueue<Task> queue)
        {
            this.queue = queue;
        }

        public void run()
        {
            Random r = new Random();

            while (! Thread.currentThread().isInterrupted()) {
                Task item = null;

                try {
                    item = queue.take();
                    System.out.println(Thread.currentThread().getName()
                                       + " consuming: " + item);
                    Thread.sleep(r.nextInt(1000));
                }
                catch (InterruptedException inte) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * Producer.
     */
    private static class Producer extends Thread
    {
        private static final String[] DATA =
            {
                "a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u",
                "v", "w",
                "x", "y", "z"
            };

        private final SynchronousQueue<Task> queue;

        public Producer(SynchronousQueue<Task> queue)
        {
            this.queue = queue;
        }

        public void run()
        {
            Random r = new Random();

            while (! Thread.currentThread().isInterrupted()) {
                String data = DATA[r.nextInt(DATA.length)];
                Task item =
                    new Task(data + "(" + Thread.currentThread().getName()
                             + ")");

                try {
                    Thread.sleep(r.nextInt(5000));
                    System.out.println(Thread.currentThread().getName()
                                       + " queuing: " + item);
                    queue.put(item);
                }
                catch (InterruptedException inte) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * The task to be produced/consumed.
     */
    private static class Task
    {
        private final String message;

        public Task(String message)
        {
            this.message = message;
        }

        public String toString()
        {
            return message;
        }
    }

    /* *************Data memebers ***********************/

    private final SynchronousQueue<Task> queue = new SynchronousQueue<Task>();

    private final int consumerThreads = 2;
    private final int producerThreads = 1;

    /**
     * C'tor.
     */
    public SynchronousQueueTest()
    {
        System.out.println("Starting...");

        // Start the consumers.
        for (int i = 0; i < consumerThreads; i++) {
            Consumer c = new Consumer(queue);
            c.setName("Consumer " + i);
            c.start();
        }

        // Start the producers.
        for (int i = 0; i < producerThreads; i++) {
            Producer p = new Producer(queue);
            p.setName("Producer " + i);
            p.start();
        }
    }

    /**
     * Main.
     */
    public static void main(String[] args)
    {
        new SynchronousQueueTest();
    }
}
