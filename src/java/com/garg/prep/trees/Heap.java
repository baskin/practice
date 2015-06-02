package com.garg.prep.trees;

/**
 * A max (min) Heap is a complete binary max (min) tree.
 */
public class Heap implements Cloneable
{
	// Best represented as an array.
	// Complete binary tree.
	protected int [] myHeap = new int[100];
	protected int myLastIndex = 0;
	protected boolean myIsMax;
	
	public Heap()
	{
		this(new int[0]);
	}
	
	public Heap(int[] array)
	{
		this(array, true);
	}
	
	public Heap(int[] array, boolean isMax)
	{
		myIsMax = isMax;
		for (int i = 0; i < array.length; i++) {
			myHeap[i + 1] = array[i];
		}
		myLastIndex = array.length;
		heapify();
	}

	protected void heapify()
	{
		// Heap starts at index 1.
		for (int i = myLastIndex / 2; i >= 1; i--) {
			ensureSubHeap(i);
		}
	}

	protected void ensureSubHeap(int root)
	{
		ensureSubHeap(root, true);
	}
	
	/**
	 * Ensures that the heap rooted at index 'root' is a max(min)heap. 
	 */
	protected void ensureSubHeap(int root, boolean balanceChild)
	{
		int rootValue = myHeap[root];
		int childIndex = 2 * root;
		if (childIndex <= myLastIndex) {
			int swapChild = childIndex;
			if (childIndex + 1 <= myLastIndex) {
				if (myIsMax) {
					// swap with the bigger child
					swapChild = (myHeap[childIndex] > myHeap[childIndex + 1]) ? 
			            childIndex : childIndex + 1;
				}
				else {
					// swap with the smaller child
					swapChild = (myHeap[childIndex] < myHeap[childIndex + 1]) ? 
			            childIndex : childIndex + 1;
				}
			}
			boolean doSwap = myIsMax ? myHeap[root] < myHeap[swapChild]
			                         : myHeap[root] > myHeap[swapChild];
			if (doSwap) {
				myHeap[root] = myHeap[swapChild];
				myHeap[swapChild] = rootValue;
				if (balanceChild) {
					ensureSubHeap(swapChild, balanceChild);
				}
			}
		}
	}
	
	public boolean insert(int value)
	{
		if (myLastIndex + 1 >= myHeap.length) {
			return false;
		}
		myLastIndex++;
		myHeap[myLastIndex] = value;
		for (int i = myLastIndex/2; i >= 1; i=i/2) { 
			// involves more comparisons than required.
			// instead:
			// i = myLastIndex;
			// while (i > 1 && myHeap[i] > myHeap[i/2]) {
			//     swap(myHeap, i, i/2);
			// }
			ensureSubHeap(i, false);
		}
		return true;
	}
	
	public int pop()
	{
		if (isEmpty()) {
			throw new RuntimeException("pop() invoked on empty heap.");
		}
		int max = myHeap[1];
		myHeap[1] = myHeap[myLastIndex];
		myLastIndex--;
		ensureSubHeap(1);
		return max;
	}
	
	public int peek()
	{
		if (isEmpty()) {
			throw new RuntimeException("pop() invoked on emtoy heap.");
		}
		return myHeap[1];
	}
	
	public int size()
	{
		return myLastIndex;
	}
	
	public boolean isEmpty()
	{
		return myLastIndex <= 0;
	}
	
	public void print()
	{
		for (int i = 1, level = 0; i <= myLastIndex; i++) {
			if (i == (1 << level)) {
				level++;
				System.out.println();
			}
			System.out.print(myHeap[i] + " ");
		}
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		Heap clone = (Heap) super.clone();
		clone.myHeap = myHeap.clone();
		clone.myLastIndex = myLastIndex;
		clone.myIsMax = myIsMax;
		return clone;
	}
	
	public int[] sort()
	{
		try {
			Heap clone = (Heap) clone();
			int[] sorted = new int[clone.size()];
			for (int i = 0; i < sorted.length; i++) {
				sorted[i] = clone.pop();
			}
			return sorted;
		}
		catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args)
	{
		int[] array = {12, 10, 56, 34, 23, 7, -5, 80, 2};
		Heap heap = new Heap(array);
		heap.print();
		heap.insert(40);
		heap.print();
		heap.pop();
		heap.print();
		System.out.println();
		int[] sorted = heap.sort();
		for (int i = 0; i < sorted.length; i++) {
			System.out.print(sorted[i] + " ");
		}
	}
}