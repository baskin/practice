package com.garg.concepts.concurrency;

import java.util.LinkedList;
import java.util.Queue;

public class SimpleBlockingQueue<T>
{
	private final Queue<T> queue = new LinkedList<T>();
	private final int bound;
	
	public SimpleBlockingQueue(int bound)
	{
		this.bound = bound;
	}
	
	public synchronized void enqueue(T e) throws InterruptedException
	{
		while (queue.size() == bound) {
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
		if (queue.size() == bound) {
			notify();
		}
		return e;
	}
}
