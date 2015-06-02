package com.garg.concepts.ds;

import java.util.Iterator;

public class LinkedList
{
	public static class Node<T>
	{
		public T data;
		public Node<T> next;
		public Node(T d)
		{
			data = d;
		}
	}
	
	private static class Itr implements Iterator<Node<Integer>>
	{
		private Node<Integer> current = null;
		private Node<Integer> pCurrent = null;

		public Itr(LinkedList list)
		{
			// current initially points to a dummy node behind list.head
			current = new Node<Integer>(null); 
			current.next = list.head;
		}
		
		@Override
		public boolean hasNext()
		{
			return current.next != null;
		}

		@Override
		public Node<Integer> next()
		{
			if (current.next == null) {
				throw new IllegalStateException();
			}
			pCurrent = current;
			current = current.next;
			return current;
		}

		@Override
		public void remove()
		{
			pCurrent.next = current.next;
		}
	}
	
	private Node<Integer> head;
	
	public LinkedList()
	{
	}
	
	public Node<Integer> getHead()
	{
		return head;
	}
	
	public Iterator<Node<Integer>> iterator()
	{
		return new Itr(this);
	}
	
	public void reverse()
	{
		Node<Integer> current = head, pCurrent = null;
		while (current != null) {
			Node<Integer> nCurrent = current.next;
			current.next = pCurrent;
			pCurrent = current;
			current = nCurrent;
		}
		head = pCurrent;
	}
	
	public void recursiveReverse()
	{
		Node<Integer> last = recursiveReverse(head);
		// Now it is a circular list.
		if (last != null) {
			last.next = null; // IMPORTANT
		}
	}
	
	private Node<Integer> recursiveReverse(Node<Integer> x)
	{
		if (x == null || x.next == null) {
			head = x;
		}
		else {
			Node<Integer> y = recursiveReverse(x.next);
			y.next = x;
		}
		return x;
	}
	
	public void print()
	{
		for (Node<Integer> c = head; c != null; c = c.next) {
			System.out.print(c.data + " ");
		}
		System.out.println();
	}
	
	public static void main(String[] args)
	{
		LinkedList list = new LinkedList();
		list.head = new Node<Integer>(1);
		Node<Integer> n = list.head.next = new Node<Integer>(3);
		n.next = new Node<Integer>(-5);
		n.next.next = new Node<Integer>(10);
		
		Iterator<Node<Integer>> itr = list.iterator();
		while (itr.hasNext()) {
			System.out.print(itr.next().data + " ");
		}
		
		System.out.println();
		itr = list.iterator();
		System.out.print(itr.next().data + " ");
		System.out.print(itr.next().data + " ");
		itr.remove();
		System.out.print(itr.next().data + " ");
		System.out.print(itr.next().data + " ");
		
		System.out.println();
		itr = list.iterator();
		while (itr.hasNext()) {
			System.out.print(itr.next().data + " ");
		}
		System.out.println();
		
		list.print();
		list.reverse();
		list.print();
		list.recursiveReverse();
		list.print();
	}
}