/*
 * Test.java
 *
 * $HeadURL: https://bhupi-practice.googlecode.com/svn/trunk/src/java/com/garg/test/TestRegex.java $
 */

/*
 * Copyright (c) 2010 D. E. Shaw & Co., L.P. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of D. E. Shaw & Co., L.P. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with D. E. Shaw & Co., L.P.
 */

package com.garg.prep.trees;

public class Serialize
{
	/**
	 * Build a binary tree from the preorder and inorder listing of the tree.
	 * 
	 * @return the root node of such a tree.
	 */
	public static TreeNode buildTree(int[] preorder, int[] inorder)
	{
		return buildTreeHelper(
			preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
	}

	/**
	 * Create and return binary tree from the preorder[prel:preh] and inorder[inl:inh] 
	 * listing of the tree.
	 */
	private static TreeNode buildTreeHelper(
		int[] preorder, int prel, int preh, int[] inorder, int inl, int inh)
	{
		// Pre-order and in-order listing sizes should be equal;
		assert (preh - prel) == (inh - inl);
		
		// Root is the first element in the preorder listing.
		int root = preorder[prel];
		
		if (prel == preh) {
			 // Leaf node
			return new TreeNode(root);
		}
		
		// Search for root in inorder listing.
		int rootInInorder = locate(inorder, inl, root);

		// Number of nodes in leftsubtree. 
		// Useful to partition pre order listing.
		int leftTreeSize = rootInInorder - inl;

		// preorder[inl+1:inl+leftTreeSize] forms left subtree of root
		// inorder[inl:rootInInorder-1] forms left subtree of root
		TreeNode left  = 
			buildTreeHelper(preorder, inl + 1, inl + leftTreeSize, 
					        inorder, inl, rootInInorder - 1);
		
		// preorder[inl+leftTreeSize+1:inh] forms right subtree of root.
		// inorder[rootInInorder+1:inh] forms right subtree of root
		TreeNode right = 
			buildTreeHelper(preorder, inl + leftTreeSize + 1, inh, 
					        inorder, rootInInorder + 1, inh);

		// Create and return the root.
		return new TreeNode(root, left, right);
	}

	/**
	 * Return the index of 'value' in array 'a[]' beginning from 'start'.
	 */
	private static int locate(int[] a, int start, int value)
	{
		int i = start;
		while (a[i] != value) {
			i++;
		}
		return i;
	}
}
