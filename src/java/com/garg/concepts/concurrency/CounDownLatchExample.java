package com.garg.concepts.concurrency;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Here is a pair of classes in which a group of worker threads use two 
 * countdown latches:
 * The first is a start signal that prevents any worker from proceeding until 
 * the driver is ready for them to proceed; 
 * The second is a completion signal that allows the driver to wait until all 
 * workers have completed. 
 * 
 * @author gargb
 */
public class CounDownLatchExample 
{
	private static class TaskManager extends Thread
	{
		private static int NUM_WORKERS = 10;
		
		public TaskManager() 
		{
			super("Manager");
		}
		
		@Override
		public void run()
		{
			CountDownLatch startSignal = new CountDownLatch(1);
			CountDownLatch doneSignal = new CountDownLatch(NUM_WORKERS);
			for (int i = 0 ; i < NUM_WORKERS; i++) {
				// Instead use a Executor
				new Worker(startSignal, doneSignal, String.valueOf(i)).start();
			}
			
			try {
				log("Start.");
				startSignal.countDown();
				
				doneSignal.await();
				// Memory consistency effects: Actions in a thread prior to 
				// calling countDown() happen-before actions following a 
				// successful return from a corresponding await() in another
				// thread.
				log("All done.");
			}
			catch (InterruptedException e) {
				log(e.getMessage());
			}
		}		
	}
	
	private static class Worker extends Thread
	{
		private final CountDownLatch myStartSignal;
		private final CountDownLatch myDoneSignal;
		
		Worker(CountDownLatch ss, CountDownLatch ds, String id)
		{
			super("Worker" + id);
			myStartSignal = ss;
			myDoneSignal = ds;
		}
		
		@Override
		public void run()
		{
			try {
				myStartSignal.await();
				
				// Do stuff.
				Thread.sleep(Math.abs(new Random().nextInt(10000)));
				
				// Memory consistency effects: Actions in a thread prior to 
				// calling countDown() happen-before actions following a 
				// successful return from a corresponding await() in another
				// thread.
				log("Done.");
				myDoneSignal.countDown();				
			}
			catch (InterruptedException e) {
				System.out.println(e);
			}
		}
	}
	
	private static void log(String msg)
	{
		System.out.println(Thread.currentThread().getName() + ": " + msg);
	}
	
	/**
	 * Begin the race!
	 */
	public static void main (String...strings)
	{
		new TaskManager().start();
	}
}
