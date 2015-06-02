/*
 * FixedWorkExample.java
 *
 * $Header$
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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A nice idiom with BlockingQueue for performing a fixed amount of work.
 * This idiom works on 1 to N chunks at a time efficiently and succinctly.
 * An example use is batching SQL updates. At the top of the loop begin the
 * batch transaction after take() and finish the batch after the processing
 * loop.
 *
 * @author gargb
 * @version $Id: FixedWorker.java 4 2011-02-18 10:00:02Z bhupi.iit@gmail.com $
 */
public class FixedWorker extends Thread
{
    private static final int N = 10;

    private BlockingQueue<Task> myQueue;

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

    /** C'tor. */
    public FixedWorker()
    {
    	super("Consumer");
        myQueue = new LinkedBlockingQueue<Task>();
    }

    /** {@inheritDoc}*/
    @Override
    public void run()
    {
        while (true) {
            final List<Task> work = new ArrayList<Task>(N);

            try {
                // Wait until there is some work
                work.add(myQueue.take());

                // Get more work, if available
                myQueue.drainTo(work, N - 1);

                System.out.println("# of tasks: " + work.size());
                for (final Task item : work) {
                    process(item);
                }

                work.clear();
            }
            catch (InterruptedException e) {
                System.out.println("Interuppting consumer.");
                Thread.currentThread().interrupt();
            }
        }
    }

    /** Add task to the consumer. */
    public void put(Task item)
    {
        myQueue.offer(item);
    }

    /** Process the task. */
    private void process(Task item) throws InterruptedException
    {
        System.out.println("processing task: " + item);
        Thread.sleep(100);
    }

    /** Test. */
    public static void main(String... args)
    {
        FixedWorker worker = new FixedWorker();
        worker.start();

        Random random = new Random();
        Thread.currentThread().setName("Producer");

        while (! Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(Math.abs(10 + random.nextInt(200)));
                worker.put(new Task(Character.toString(
                                        (char) ('a'
                                                + random.nextInt(25)))));
            }
            catch (InterruptedException e) {
                System.out.println("Interuppting producer.");
                Thread.currentThread().interrupt();
            }
        }
    }
}
