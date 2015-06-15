package com.garg.concepts.concurrency;

import java.util.concurrent.CountDownLatch;

public class VolatileIncTest {
	private static final int NUM_THREADS = 16;
	private static final int LOOP_COUNT = 1024 * 1024;

	// Instead AtomicLong should work.
	private static long data = 0;

	private class Incrementor implements Runnable {
		@Override
		public void run() {
			for (int i = 0; i < LOOP_COUNT; i++) {
				incrementData();
			}
		}
	}

	private static class ObediantWorker extends Thread {
		private final CountDownLatch myStartSignal;
		private final CountDownLatch myDoneSignal;

		public ObediantWorker(Runnable run, CountDownLatch startSignal,
				CountDownLatch doneSignal) {
			super(run);
			myStartSignal = startSignal;
			myDoneSignal = doneSignal;
		}

		@Override
        public void run() {
			try {
				myStartSignal.await();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}

			super.run();
			myDoneSignal.countDown();
		}
	}

	public long getData() {
		return data;
	}

	private void incrementData() {
		data++;
	}

	public Runnable getIncrementor() {
		return new Incrementor();
	}

	public static void main(String... strings) {
		VolatileIncTest test = new VolatileIncTest();
		CountDownLatch startSignal = new CountDownLatch(1);
		CountDownLatch doneSignal = new CountDownLatch(NUM_THREADS);

		for (int i = 0; i < NUM_THREADS; i++) {
			new ObediantWorker(test.getIncrementor(), startSignal, doneSignal)
					.start();
		}

		System.out.println(Thread.currentThread().getName() + ": value="
				+ NUM_THREADS * LOOP_COUNT);
		startSignal.countDown();

		try {
			doneSignal.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(Thread.currentThread().getName() + ": value="
				+ test.getData());
	}
}
