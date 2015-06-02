package com.garg.prep.ds;

public class ArrayQueue
{
	public static class QueueOverflowException extends Exception {}
	public static class QueueUnderflowException extends Exception {}
	
	public class Iterator implements java.util.Iterator<Integer> 
	{
		int index = myHead - 1;
		
		@Override
		public boolean hasNext() 
		{
			return size() != 0; 
		}

		@Override
		public Integer next() 
		{
			index = (index + 1) % myCapcacity;
			return myQueue[index];
		}

		@Override
		public void remove() 
		{
			for (int i = 0; (index + i+1) % myCapcacity < myTail; i++) {
				myQueue[(index + i) % myCapcacity] = myQueue[(index + i+1) % myCapcacity];
			}
			myTail--;
			if (myTail < 0) {
				myTail += myCapcacity;
			}
		}
	}
	
	private final int[] myQueue;
	private final int myCapcacity;
	// index containing first element.
	private int myHead = 0;
	// index after the last element. 
	private int myTail = 0;
	
	public ArrayQueue(int capacity) 
	{
		myCapcacity = capacity;
		myQueue = new int[myCapcacity];
	}
	
	public ArrayQueue() 
	{
		this(1024);
	}
	
	public int size()
	{
		int gap = myTail - myHead;
		int size = gap;
		if (gap < 0) {
			// Wrapped around.
			size = myCapcacity + gap;
		}
		return size;
	}
	
	public void enqueue(int value) throws QueueOverflowException
	{
		if ((myTail + 1)%myCapcacity == myHead) {
			throw new QueueOverflowException();
		}
		myQueue[myTail] = value;
		myTail++;
		myTail %= myCapcacity; // wrap around.
	}
	
	public int dequeue() throws QueueUnderflowException
	{
		if (myHead == myTail) {
			throw new QueueUnderflowException();
		}
		int value = myQueue[myHead];
		myHead++;
		myHead %= myCapcacity;
		return value;
	}
	
	public void print()
	{
		System.out.printf(
			"Head:%d Tail:%d Capacity:%d Q:{", myHead, myTail, myCapcacity);
		for (int i = 0; i < size(); i++) {
			System.out.print(myQueue[(myHead + i) % myCapcacity] + " ");
		}
		System.out.println("}");
	}
	
	public Iterator iterator()
	{
		return new Iterator();
	}
	
	public static void main(String[] args) 
		throws QueueOverflowException, QueueUnderflowException 
	{
		ArrayQueue q = new ArrayQueue(4);
		q.enqueue(5);
		q.print();
		q.enqueue(4);
		q.print();
		q.enqueue(7);
		q.print();
		q.dequeue();
		q.print();
		q.dequeue();
		q.print();
		q.dequeue();
		q.print();
		q.enqueue(8);
		q.print();
		q.enqueue(1);
		q.print();
		q.enqueue(22);
		q.print();
		
		java.util.Iterator<Integer> itr = q.iterator();
		itr.remove();
		q.print();
		itr = q.iterator();
		itr.next();
		itr.remove();
		q.print();
	}
}