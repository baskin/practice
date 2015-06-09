package com.garg.concepts.ds.trees;

import javafx.util.Pair;

/**
 * Write a recursive function treeToList(Node root) that takes an ordered binary
 * tree and rearranges the internal pointers to make a circular doubly linked
 * list out of the tree nodes. The "previous" pointers should be stored in the
 * "left" field and the "next" pointers should be stored in the "right" field.
 * The list should be arranged so that the nodes are in increasing order.
 * 
 * @author bgarg
 */
public class Tree2List
{
    public static TreeNode tree2List(final TreeNode node)
    {
        if (null == node) {
            return null;
        }
        Pair<TreeNode, TreeNode> list = tree2ListInner(node);
        list.getKey().leftChild = list.getValue();
        list.getValue().rightChild = list.getKey();
        return list.getKey();
    }
    
    /**
     * Returns a pair of first and last nodes in the list formed under the tree
     * rooted by the given node. Destroys the tree in the given process.
     * 
     * POST ORDER.
     */
    private static Pair<TreeNode, TreeNode> tree2ListInner(final TreeNode node)
    {
        if (null == node) {
            return null;
        }

        Pair<TreeNode, TreeNode> l = tree2ListInner(node.leftChild);
        Pair<TreeNode, TreeNode> r = tree2ListInner(node.rightChild);
        
        TreeNode head = node;
        TreeNode tail = node;
        if (null != l) {
            l.getValue().rightChild = node;
            node.leftChild = l.getValue();
            head = l.getKey();
        }
        if (null != r) {
            node.rightChild = r.getKey();
            r.getKey().leftChild = node;
            tail = r.getValue();
        }
        return new Pair<TreeNode, TreeNode>(head, tail);
    }

    public static void main(String[] args)
    {
        TreeNode node = TreeUtil.getDummyTree();
        TreeUtil.printTree(node, TreeUtil.TraversalOrder.IN);
        System.out.println();
        
        TreeNode head = tree2List(node);
        TreeNode current = head;
        System.out.print(current + " ");
        current = current.rightChild;
        while (current != head) {
            System.out.print(current + " ");
            current = current.rightChild;
        }
    }
}
