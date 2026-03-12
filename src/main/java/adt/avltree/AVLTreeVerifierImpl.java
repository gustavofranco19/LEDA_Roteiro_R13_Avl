package adt.avltree;

import adt.bst.BSTNode;
import adt.bst.BSTVerifierImpl;

/**
 * Performs consistency validations within a AVL Tree instance
 * 
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class AVLTreeVerifierImpl<T extends Comparable<T>> extends BSTVerifierImpl<T> implements AVLTreeVerifier<T> {

	private AVLTreeImpl<T> avlTree;

	public AVLTreeVerifierImpl(AVLTree<T> avlTree) {
		super(avlTree);
		this.avlTree = (AVLTreeImpl<T>) avlTree;
	}

	private AVLTreeImpl<T> getAVLTree() {
		return avlTree;
	}

	@Override
	public boolean isAVLTree() {
		return isBST() && isAVLTree(this.getAVLTree().getRoot());
	}
	private boolean isAVLTree(BSTNode<T> node) {
		boolean result = true;
		if (!node.isEmpty()) {
			int balance = avlTree.calculateBalance(node);
			if (balance < -1 || balance > 1) {
				result = false;
			} else {
				boolean left = isAVLTree((BSTNode<T>) node.getLeft());
				boolean right = isAVLTree((BSTNode<T>) node.getRight());
				result = left && right;
			}
		}
		return result;
	}
}
