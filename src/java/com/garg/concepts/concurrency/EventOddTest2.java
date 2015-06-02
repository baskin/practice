package com.garg.concepts.concurrency;

import java.util.concurrent.Semaphore;

public class EventOddTest2 extends Thread
{
	private final Semaphore even;
	private final Semaphore odd;
	private final boolean oddPrinter;
	private int count = 1;

	public EventOddTest2(Semaphore odd, Semaphore even, boolean oddPrinter)
	{
		super(oddPrinter ? "odd" : "even");
		this.odd = odd;
		this.even = even;
		this.oddPrinter = oddPrinter;
		if (!oddPrinter) {
			count = 2;
		}
	}

	@Override
	public void run()
	{
		while (count < 50) {
			if (oddPrinter) {
				try {
					odd.acquire();
				}
				catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				System.out.print(count + "* ");
				count += 2;
				even.release();
			}
			else {
				try {
					even.acquire();
				}
				catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				System.out.print(count + ": ");
				count += 2;
				odd.release();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException
	{
		Semaphore even = new Semaphore(1);
		Semaphore odd = new Semaphore(1);
		even.acquire();
		EventOddTest2 evenThread = new EventOddTest2(odd, even, false);
		EventOddTest2 oddThread  = new EventOddTest2(odd, even, true);
		evenThread.start();
		oddThread.start();
	}
}
