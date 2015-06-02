package com.garg.concepts.ds.trees;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class TreeIterator implements Iterator<TreeNode>
{
	public static class TreePreIterator extends TreeIterator
	{
		private Stack<TreeNode> stack = new Stack<TreeNode>();

		public TreePreIterator(TreeNode root)
		{
			stack.push(root);
		}

		@Override
		public boolean hasNext()
		{
			return !stack.isEmpty();
		}

		@Override
		public TreeNode next()
		{
			if (stack.isEmpty()) {
				throw new NoSuchElementException();
			}
			// preorder
			TreeNode popped = stack.pop();
			if (popped.rightChild != null) {
				stack.push(popped.rightChild);
			}
			if (popped.leftChild != null) {
				stack.push(popped.leftChild);
			}
			return popped;
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}

	public static class TreeInIterator extends TreeIterator
	{
		private Stack<TreeNode> stack = new Stack<TreeNode>();

		public TreeInIterator(TreeNode root)
		{
			TreeNode current = root;
			while (current != null) {
				stack.push(current);
				current = current.leftChild;
			}
		}

		@Override
		public boolean hasNext()
		{
			return !stack.isEmpty();
		}

		@Override
		public TreeNode next()
		{
			if (stack.isEmpty()) {
				throw new NoSuchElementException();
			}
			TreeNode popped = stack.pop();
			if (popped.rightChild != null) {
				TreeNode current = popped.rightChild;
				while (current != null) {
					stack.push(current);
					current = current.leftChild;
				}
			}
			return popped;
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}
	
	public static class TreePostIterator  extends TreeIterator
	{
		private Stack<TreeNode> stack = new Stack<TreeNode>();

		public TreePostIterator(TreeNode root)
		{
			TreeNode current = root;
			while (current != null) {
				stack.push(current);
				if (current.rightChild != null) {
					stack.push(current.rightChild);
				}
				current = current.leftChild;
			}
		}

		@Override
		public boolean hasNext()
		{
			return !stack.isEmpty();
		}

		@Override
		public TreeNode next()
		{
			// XXX fixme
			if (stack.isEmpty()) {
				throw new NoSuchElementException();
			}
			TreeNode popped = stack.pop();
			TreeNode current = popped;
			while (current != null) {
				stack.push(current);
				if (current.rightChild != null) {
					stack.push(current.rightChild);
				}
				current = current.leftChild;
			}
			return stack.pop();
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public boolean hasNext()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public TreeNode next()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void remove()
	{
		throw new UnsupportedOperationException();
	}
}
