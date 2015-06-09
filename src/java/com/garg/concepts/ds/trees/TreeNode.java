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
	public int freq = -1;
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
	
	public boolean isLeaf() 
	{
	    return null == leftChild && null == rightChild;
	}
	
	@Override
	public String toString()
	{
	    return "{" + String.valueOf(data) + ":" + String.valueOf(freq) + "}";
	}
	
	@Override
	public boolean equals(Object obj)
	{
	    if (!(obj instanceof TreeNode)) {
	        return false;
	    }
	    TreeNode other = (TreeNode) obj;
	    return data == other.data && freq == other.freq;
	}
	
	@Override
	public int hashCode()
	{
	    return data * 31 + freq;
	}
}

