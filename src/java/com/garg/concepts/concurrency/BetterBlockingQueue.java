package com.garg.concepts.concurrency;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

// TODO
public class BetterBlockingQueue<T>
{
	private final Queue<T> queue = new LinkedList<T>();
	private final AtomicInteger bound;
	
	public BetterBlockingQueue(int bound)
	{
		this.bound = new AtomicInteger(bound);
	}
	
	public synchronized void enqueue(T e) throws InterruptedException
	{
		while (queue.size() == bound.intValue()) {
			wait();
		}
		queue.add(e);
		if (queue.size() == 0) {
			notify();
		}
	}
	
	public synchronized T dequeue() throws InterruptedException
	{
		if (queue.size() == 0) {
			wait();
		}
		T e = queue.poll();
		if (queue.size() == bound.intValue()) {
			notify();
		}
		return e;
	}
}
