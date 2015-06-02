package com.garg.prep.trees;
import static com.garg.prep.trees.TreeUtil.*;


public class TreeUtilExtended
{
	
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
	
	public static void main(String[] args)
	{
		TreeNode node = getDummyTree();
		TreeNode ancestor = commonAncestor(node, new TreeNode(-56), new TreeNode(2));
		if (ancestor != null) {
			System.out.println(ancestor.data);
		}
	}
}
