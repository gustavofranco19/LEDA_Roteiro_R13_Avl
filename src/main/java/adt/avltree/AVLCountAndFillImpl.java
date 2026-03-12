package adt.avltree;

import java.util.Arrays;

import adt.bst.BSTNode;
import adt.bt.Util;

public class AVLCountAndFillImpl<T extends Comparable<T>> extends
		AVLTreeImpl<T> implements AVLCountAndFill<T> {

	private int LLcounter;
	private int LRcounter;
	private int RRcounter;
	private int RLcounter;

	private boolean rebalanceEnabled = true;


	public AVLCountAndFillImpl() {
	
	}

	@Override
	public int LLcount() {
		return LLcounter;
	}

	@Override
	public int LRcount() {
		return LRcounter;
	}

	@Override
	public int RRcount() {
		return RRcounter;
	}

	@Override
	public int RLcount() {
		return RLcounter;
	}

	@Override
	public void fillWithoutRebalance(T[] array) {
		if (array != null && array.length > 0) {
			this.rebalanceEnabled = false;
			Arrays.sort(array);
			fillWithoutRebalance(array, 0, array.length-1);
			this.rebalanceEnabled = true;
		}
	}

	private void fillWithoutRebalance(T[] array, int left, int right) {
		if (left <= right) {
			int mid = (left + right) / 2;
			insert(array[mid]);
			fillWithoutRebalance(array, left, mid - 1);
			fillWithoutRebalance(array, mid + 1, right);
		}
	}

	@Override
	protected void rebalance(BSTNode<T> node) {
		if (!node.isEmpty() && this.rebalanceEnabled) {
			BSTNode<T> parent = (BSTNode<T>) node.getParent();
			int balance = calculateBalance(node);
			BSTNode<T> subTreeRoot = node;
			BSTNode<T> child;
			if (balance > 1) {
				if (calculateBalance((BSTNode<T>) node.getLeft()) >= 0) {
					this.LLcounter++;
					subTreeRoot = Util.rightRotation(node);
				} else {
					this.LRcounter++;
					child = Util.leftRotation((BSTNode<T>) node.getLeft());
					node.setLeft(child);
					subTreeRoot = Util.rightRotation(node);
				}
			} else if (balance < -1) {
				if (calculateBalance((BSTNode<T>) node.getRight()) <= 0) {
					this.RRcounter++;
					subTreeRoot = Util.leftRotation(node);
				} else {
					this.RLcounter++;
					child = Util.rightRotation((BSTNode<T>) node.getRight());
					node.setRight(child);
					subTreeRoot = Util.leftRotation(node);
				}
			}
			if (subTreeRoot != node) {
				if (parent == null) {
					this.root = subTreeRoot;
				} else if (parent.getLeft() == node) {
					parent.setLeft(subTreeRoot);
				} else {
					parent.setRight(subTreeRoot);
				}
			}
			subTreeRoot.setParent(parent);
		}
	}
}
