package com.garg.concepts.ds.trees;
import static com.garg.concepts.ds.trees.TreeUtil.contains;
import static com.garg.concepts.ds.trees.TreeUtil.getDummyTree;


public class TreeUtilExtended
{
	
    /**
     * LCA in binary tree.
     * 
     * Time Complexity: O(N^2) 
     */
	public static TreeNode commonAncestor(TreeNode root, TreeNode pNode, TreeNode qNode)
	{
		int p = pNode.data;
		int q = qNode.data;
		
		TreeNode current = root;
		while (current != null) {
			if (contains(current.leftChild, p) && contains(current.leftChild, q)) {
				current = current.leftChild;
			}
			else if (contains(current.rightChild, p) && contains(current.rightChild, q)) {
				current = current.rightChild;
			}
			else {
				return current;
			}
		}
		return null;
	}
	
	/**
	 * LCA in binary tree. We optimize the above algo, by merging contains() method
	 * with the LCA determination logic.
	 * 
     * Time Complexity: O(N) 
     */
    public static TreeNode commonAncestorOptimal(TreeNode root, TreeNode pNode, TreeNode qNode)
    {
        if (null == root) {
            return null;
        }
        if (pNode == root || qNode == root || root.equals(pNode) || root.equals(qNode)) {
            return root;
        }
        TreeNode left = commonAncestorOptimal(root.leftChild, pNode, qNode);
        TreeNode right = commonAncestorOptimal(root.rightChild, pNode, qNode);
        if (null != left && null != right) {
            return root;
        }
        if (null == left) {
            return right;
        }
        return left;
    }
	
	public static void printAllPaths(final TreeNode root) 
	{
	    int [] path = new int[100];
	    printAllPaths(root, path, 0);
	}
	
	/**
     * Print all paths from root to leaves.
     */
    private static void printAllPaths(final TreeNode current, int []path, int pathIndex) 
    {
        if (null == current) {
            return;
        }
        path[pathIndex] = current.data;
        if (current.isLeaf()) {
            for (int i = 0; i <= pathIndex; i++) {
                System.out.print(path[i] + " ");
            }
            System.out.println();
        }
        printAllPaths(current.leftChild, path, pathIndex + 1);
        printAllPaths(current.rightChild, path, pathIndex + 1);
    }
	
	/**
     * Print all ancestors of node in the tree with root as current.
     */
	public static boolean printAncestors(final TreeNode current, final TreeNode node) 
	{
	    if (current == null || node == null) {
            return false;
        }
        if (current == node || current.data == node.data) {
            System.out.println(current.data);
            return true;
        }
        if (printAncestors(current.leftChild, node) || printAncestors(current.rightChild, node)) {
            System.out.println(current.data);
            return true;
        }
        return false;
	}
	
	public static void main(String[] args)
	{
		TreeNode node = getDummyTree();
		System.out.println(commonAncestor(node, new TreeNode(-56), new TreeNode(2)));
		System.out.println(commonAncestorOptimal(node, new TreeNode(-56), new TreeNode(2)));
		
		System.out.println();
		TreeNode searchNode = new TreeNode(-7); 
		System.out.println("Ancestors of node:" + searchNode);
		printAncestors(node, searchNode);
		
		System.out.println();
		System.out.println("Printing all leaf paths");
		printAllPaths(node);
	}
}
