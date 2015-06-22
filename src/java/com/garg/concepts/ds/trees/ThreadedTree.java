package com.garg.concepts.ds.trees;

import static com.garg.concepts.ds.trees.TreeUtil.getDummyTree;

import com.garg.concepts.ds.trees.TreeNode.ThreadedTreeNode;
import com.garg.concepts.ds.trees.TreeUtil.TraversalOrder;
import com.garg.concepts.ds.trees.TreeUtil.Visitor;

/**
 * Inorder traversal of a Binary tree is either be done using recursion or with
 * the use of a auxiliary stack. The idea of threaded binary trees is to make
 * inorder traversal faster and do it without stack and without recursion. A
 * binary tree is made threaded by making all right child pointers that would
 * normally be NULL point to the inorder successor of the node (if it exists).
 * 
 * http://geeksquiz.com/threaded-binary-tree/
 * 
 * @author bgarg
 */
public class ThreadedTree
{
    public static ThreadedTreeNode toThreadedTree(final TreeNode root)
    {
        ThreadedTreeNode newRoot = copyAsThreaded(root);
        toThreadedTree(newRoot);
        return newRoot;
    }

    private static ThreadedTreeNode copyAsThreaded(TreeNode root)
    {
        if (null == root) {
            return null;
        }

        ThreadedTreeNode left = copyAsThreaded(root.leftChild);
        ThreadedTreeNode right = copyAsThreaded(root.rightChild);
        ThreadedTreeNode newRoot = new ThreadedTreeNode(root.data);
        newRoot.leftChild = left;
        newRoot.rightChild = right;
        return newRoot;
    }

    /**
     * Convert the tree with the given node as root to a threaded binary tree -
     * one where a NULL rightchild is made to point to inorder successor
     * 
     * Returns the rightmost node in the subtree with the given root node
     */
    public static ThreadedTreeNode toThreadedTree(final ThreadedTreeNode root)
    {
        if (null == root) {
            return null;
        }

        ThreadedTreeNode left = toThreadedTree((ThreadedTreeNode) root.leftChild);
        ThreadedTreeNode right = toThreadedTree((ThreadedTreeNode) root.rightChild);
        if (null != left) {
            left.rightChild = root;
            left.isRightThreaded = true;
        }
        return (right == null) ? root : right;
    }

    /**
     * Do an inorder traversal of the threaded binary tree (which can be done
     * without recursion or using an auxiliary stack)
     */
    public static void inorder(ThreadedTreeNode root, Visitor visitor)
    {
        ThreadedTreeNode smallest = (ThreadedTreeNode) findLeftMost(root);
        ThreadedTreeNode current = smallest;
        while (null != current) {
            visitor.visit(current);

            // If this node is a thread node, then go to inorder successor
            if (current.isRightThreaded) {
                current = (ThreadedTreeNode) current.rightChild;
            }
            else { // Else go to the leftmost child in right subtree
                current = (ThreadedTreeNode) findLeftMost(current.rightChild);
            }
        }
    }

    /**
     * Find leftmost node in a tree rooted with 'root'
     */
    private static TreeNode findLeftMost(TreeNode root)
    {
        if (null == root) {
            return null;
        }
        while (null != root.leftChild) {
            root = root.leftChild;
        }
        return root;
    }

    public static void main(String[] args)
    {
        TreeNode node = getDummyTree();
        System.out.println("Original:");
        TreeUtil.printTree(node, TraversalOrder.IN);
        ThreadedTreeNode threadedTree = toThreadedTree(node);
        System.out.println("\nThreaded optimized inorder:");
        inorder(threadedTree, new Visitor() {

            @Override
            public boolean visit(TreeNode node)
            {
                System.out.print(" " + node.data);
                return false;
            }
        });
    }
}
