package com.garg.concepts.ds.trees;

import com.garg.concepts.ds.trees.TreeNode.IndexedTreeNode;

public class BSTree
{
	
	public static class IndexedBSTree extends BSTree
	{
		public IndexedBSTree(TreeNode root)
		{
			super(root);
		}
		
		@Override
		protected TreeNode createNode(int value)
		{
			return new TreeNode.IndexedTreeNode(value);
		}
		
		public TreeNode findNthSmallest(int n)
		{
			IndexedTreeNode node = (IndexedTreeNode) super.myRoot;
			while (node != null) {
				if (n == node.leftSize) {
					break;
				}
				if (n < node.data) {
					node = (IndexedTreeNode) node.leftChild;
				}
				else {
					node = (IndexedTreeNode) node.rightChild;
					n -= node.leftSize;
				}
			}
			return null;
		}
	}
	
	private TreeNode myRoot;
	
	public BSTree()
	{
	}
	
	public BSTree(TreeNode root)
	{
		myRoot = root;
	}
	
	public TreeNode find(int value)
	{
		TreeNode node = myRoot;
		while (node != null) {
			if (node.data == value) {
				break;
			}
			if (node.data < value) {
				node = node.rightChild;
			}
			else {
				node = node.leftChild;
			}
		}
		return node;
	}
	
	public int max()
	{
		if (myRoot == null) {
			return -1;
		}
		TreeNode x = myRoot;
		while(x.rightChild != null) {
			x = x.rightChild;
		}
		return x.data;
	}
	
	public boolean insert(int value)
	{
		return insert(createNode(value));
	}

	protected TreeNode createNode(int value)
	{
		return new TreeNode(value);
	}
	
	public boolean insert(TreeNode newNode)
	{
		TreeNode node = myRoot;
		TreeNode parent = null;
		while (node != null) {
			if (node.data == newNode.data) {
				// duplicate 
				return false;
			}
			if (node.data < newNode.data) {
				node = node.rightChild;
			}
			else {
				node = node.leftChild;
			}
			parent = node;
		}
		if (parent == null) {
			myRoot = node;
		}
		else if (parent.data > newNode.data){
			parent.leftChild = newNode;
		}
		else {
			parent.rightChild = newNode;
		}
		return true;
	}
	
	public TreeNode delete(int value) 
	{
		return null;
	}
}
