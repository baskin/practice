package com.garg.prep.trees;

import com.garg.prep.trees.BSTree;
import com.garg.prep.trees.TreeNode;

/**
 * Test convertion of a BST to a singly linked list. It should happen in-place.
 * TreeNode has 2 pointers left and right. Assume the right pointer of a {@code TreeNode}
 * stands for the 'next' pointer in a linked list.
 * 
 * @author bgarg
 */
public class BSTree2LinkedList 
{
	/**
	 *     P
	 *   /   \
	 *  L     R
	 *  
	 *  P = parent,
	 *  L = max element in left subtree,
	 *  R = min element in right subtree,
	 *  L <= P <= R
	 *  This method recursively converts the above BST into L --> P --> R
	 *  It needs to do 2 things as the base case.
	 *  1. Add L --> P
	 *  2. Add P --> R
	 *  
	 *  In-order traversal is required since ordering needs to be maintained.
	 *  
	 *  Returns array of {@code TreeNode}s where first element is head of list and 2nd element
	 *  is tail of list formed under the tree with root as p. In case p is a leaf node, both 
	 *  indices will contain p. If p is null both indices will have null.
	 */
	private TreeNode[] convert(TreeNode p)
	{
		if (p == null) {
			// Base case.
			return new TreeNode[] {null, null};
		}
		
		// Convert subtree with root as p.leftChild into list.
		TreeNode[] leftList = convert(p.leftChild);
		if (leftList[1] != null) {
			// tail ---> p
			leftList[1].rightChild = p;
		}
		// Convert subtree with root as p.rightChild into list.
		TreeNode[] rightList = convert(p.rightChild);
		// p --> head
		p.rightChild = rightList[0];
		
		// Now, we have a combined list    tailOfLeftList --> p --> headOfRightList
		// Return overall head & tail.
		TreeNode head = leftList[0] != null ? leftList[0] : p;
		TreeNode tail = rightList[1] != null ? rightList[1] : p;
		return new TreeNode[] {head, tail};
	}
	
	public static void main(String[] args) 
	{
		TreeNode root = new TreeNode(10);
		BSTree bstree = new BSTree(root);
		bstree.insert(5);
		bstree.insert(30);
		bstree.insert(40);
		bstree.insert(42);
		bstree.insert(41);
		bstree.insert(25);
		bstree.insert(-4);
		bstree.insert(3);
		bstree.insert(7);
		
		// Perform conversion.
		TreeNode[] list = new BSTree2LinkedList().convert(root);
		
		TreeNode current = list[0];
		while (current != null) {
			System.out.println(current.data);
			current = current.rightChild;
		}
	}
}
