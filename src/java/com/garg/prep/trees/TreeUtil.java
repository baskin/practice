package com.garg.prep.trees;


public class TreeUtil
{
	public static interface Visitor
	{
		public boolean visit(TreeNode node);
	}
	
	public enum TraversalOrder {
		PRE,
		IN,
		POST
	}
	
	public static void preOrder(TreeNode node, Visitor v)
	{
		if (node == null) {
			return;
		}
		if (v.visit(node)) {
			return;
		}
		preOrder(node.leftChild, v);
	    preOrder(node.rightChild, v);
	}
	
	public static void inOrder(TreeNode node, Visitor v)
	{
		if (node == null) {
			return;
		}
		inOrder(node.leftChild, v);
		if (v.visit(node)) {
			return;
		}
		inOrder(node.rightChild, v);
	}
	
	public static void postOrder(TreeNode node, Visitor v)
	{
		if (node == null) {
			return;
		}
		postOrder(node.leftChild, v);
		postOrder(node.rightChild, v);
		if (v.visit(node)) {
			return;
		}
	}
	
	public static void traverse(TreeNode node, TraversalOrder order, Visitor v)
	{
		switch (order) {
		case PRE:
			preOrder(node, v);
			break;
		case IN:
			inOrder(node, v);
			break;
		case POST:
			postOrder(node, v);
			break;
		}
	}
	
	public static TreeIterator iterator(TreeNode root)
	{
		return new TreeIterator.TreeInIterator(root);
	}
	
	public static void printTree(TreeNode node, TraversalOrder order)
	{
		class Printer implements Visitor
		{
			@Override
			public boolean visit(TreeNode node)
			{
				System.out.printf("%4s", node.data);
				return true;
			}
		}
		Visitor printer = new Printer();
		traverse(node, order, printer);
	}
	
	public static int count(TreeNode node)
	{
		class Counter implements Visitor
		{
			int count = 0;
			@Override
			public boolean visit(TreeNode node) {
				count++;
				return true;
			}
		};
		
		Counter counter = new Counter();
		traverse(node, TraversalOrder.PRE, counter);
		return counter.count;
	}
	
	public static int height(TreeNode node)
	{
		if (node == null) {
			return 0;
		}
		return 1 + Math.max(height(node.leftChild), height(node.rightChild)); 
	}
	
	public static boolean contains(final TreeNode node, final int data)
	{
		class Finder implements Visitor
		{
			boolean found = false;
			@Override
			public boolean visit(TreeNode node)
			{
				if (node.data == data) {
					found = true;
				}
				return found;
			};
		}
		Finder finder = new Finder();
		traverse(node, TraversalOrder.PRE, finder);
		return finder.found;
	}
	
	/**
	 * <pre>
	 *            10
	 *       20        30
	 *     2   -56   88
	 *                 -7
	 * </pre>
	 * <p>pre :PLR: 10 20 2 -56 30 88 -7
	 * <p>in  :LPR: 2 20 -56 10 88 -7 30
	 * <p>post:LRP: 2 -56 20 -7 88 30 10  
	 */
	public static TreeNode getDummyTree()
	{
		TreeNode n = new TreeNode(10);
		TreeNode l = n.leftChild = new TreeNode(20);
		TreeNode r = n.rightChild = new TreeNode(30);
		l.leftChild = new TreeNode(2);
		l.rightChild = new TreeNode(-56);
		r.leftChild = new TreeNode(88);
		r.leftChild.rightChild = new TreeNode(-7);
		return n;
	}
	
	public static void main(String[] args)
	{
		TreeNode node = getDummyTree();
		System.out.print("PreOrder  : ");
		printTree(node, TraversalOrder.PRE);
		System.out.println();
		System.out.print("InOrder   : ");
		printTree(node, TraversalOrder.IN);
		System.out.println();
		System.out.print("PostOrder : ");
		printTree(node, TraversalOrder.POST);
		System.out.println();
		System.out.println("Count     :" + count(node));
		System.out.println("Height    :" + height(node));
		TreeIterator itr = iterator(node);
		while (itr.hasNext()) {
			System.out.print(itr.next().data + " ");
		}
	}
}
