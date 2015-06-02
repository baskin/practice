package com.garg.concepts.ds.trees;

public class TreeNode
{
	public static class IndexedTreeNode extends TreeNode
	{
		public int leftSize;
		public IndexedTreeNode(int d)
		{
			super(d);
		}
	}
	
	public int data;
	public int freq;
	public TreeNode leftChild;
	public TreeNode rightChild;
	
	public TreeNode(int d)
	{
		data = d;
	}
	
	public TreeNode(int d, int f)
	{
		data = d;
		freq = f;
	}
	
	public TreeNode(int d, TreeNode left, TreeNode right)
	{
		leftChild = left;
		rightChild = right;
	}
}

