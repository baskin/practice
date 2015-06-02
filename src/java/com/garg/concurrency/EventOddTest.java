package com.garg.concurrency;

public class EventOddTest extends Thread
{
	private Object shared;
	private final boolean oddPrinter;
	private int count = 1;
	
	private static boolean printNextOdd = true;
	
	public EventOddTest(boolean oddPrinter, Object shared) 
	{
		super(oddPrinter ? "odd" : "even");
		this.shared = shared;
		this.oddPrinter = oddPrinter;
		if (!oddPrinter) {
			count = 2;
		}
	}
	
	@Override
	public void run() 
	{
		while (count < 100) {
			synchronized (shared) {
				try {
					while ((oddPrinter && !printNextOdd) 
					    || (!oddPrinter && printNextOdd))
						shared.wait();
					printNextOdd = !oddPrinter;
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.print(count + " ");
				count += 2;
				shared.notify();
			}
		}
	}
	
	public static void main(String[] args) 
	{
		Object shared  = new Object();
		EventOddTest odd  = new EventOddTest(true, shared);
		EventOddTest even = new EventOddTest(false, shared);
		odd.start();
		even.start();
	}
}
