package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

/**
 * 
 * Implementacao de uma arvore AVL
 * A CLASSE AVLTree herda de BSTImpl. VOCE PRECISA SOBRESCREVER A IMPLEMENTACAO
 * DE BSTIMPL RECEBIDA COM SUA IMPLEMENTACAO "OU ENTAO" IMPLEMENTAR OS SEGUITNES
 * METODOS QUE SERAO TESTADOS NA CLASSE AVLTREE:
 *  - insert
 *  - preOrder
 *  - postOrder
 *  - remove
 *  - height
 *  - size
 *
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class AVLTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements
		AVLTree<T> {

	// TODO Do not forget: you must override the methods insert and remove
	// conveniently.

	// AUXILIARY
	protected int calculateBalance(BSTNode<T> node) {
		return height((BSTNode<T>) node.getLeft()) - height((BSTNode<T>) node.getRight());
	}

	// AUXILIARY
	protected void rebalance(BSTNode<T> node) {
		if (!node.isEmpty()) {
			BSTNode<T> parent = (BSTNode<T>) node.getParent();
			BSTNode<T> subTreeRoot = node;
			BSTNode<T> child;
			int balance = calculateBalance(node);
			if (balance > 1) {
				if (calculateBalance((BSTNode<T>) node.getLeft()) >= 0) {
					subTreeRoot = Util.rightRotation(node);
				} else {
					child = Util.leftRotation((BSTNode<T>) node.getLeft());
					node.setLeft(child);
					subTreeRoot = Util.rightRotation(node);
				}
			} else if (balance < -1) {
				if (calculateBalance((BSTNode<T>) node.getRight()) <= 0) {
					subTreeRoot = Util.leftRotation(node);
				} else {
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

	// AUXILIARY
	protected void rebalanceUp(BSTNode<T> node) {
		if (node != null) {
			rebalance(node);
			rebalanceUp((BSTNode<T>) node.getParent());
		}
	}

	@Override
	public void insert(T element) {
		insert(this.root, element);
	}

	private void insert(BSTNode<T> node, T element) {
		if (element != null) {
			if (node.isEmpty()) {
				node.setData(element);
				node.setLeft(new BSTNode<>());
				node.setRight(new BSTNode<>());
				node.getLeft().setParent(node);
				node.getRight().setParent(node);
			
			} else if (element.compareTo(node.getData()) < 0) {
				insert((BSTNode<T>) node.getLeft(), element);
			
			} else if (element.compareTo(node.getData()) > 0) {
				insert((BSTNode<T>) node.getRight(), element);
			}
			rebalanceUp(node);
		}
	}

	@Override
	public void remove(T element) {
		BSTNode<T> node = search(element);
		if(!node.isEmpty()){
			remove(node);
		}
	}

	private void remove(BSTNode<T> node){
		if(node.isLeaf()){
			node.setData(null);
			rebalanceUp((BSTNode<T>) node.getParent());
		} else if(nodeHasOneChild(node)){
			if(node != root){
				if(nodeIsLeftChild(node)){
					if(!node.getLeft().isEmpty()){
						node.getParent().setLeft(node.getLeft());
						node.getLeft().setParent(node.getParent());
					} else {
						node.getParent().setLeft(node.getRight());
						node.getRight().setParent(node.getParent());
					}
				} else {
					if(!node.getLeft().isEmpty()){
						node.getParent().setRight(node.getLeft());
						node.getLeft().setParent(node.getParent());
					} else {
						node.getParent().setRight(node.getRight());
						node.getRight().setParent(node.getParent());
					}
				}
			} else {
				if(!root.getLeft().isEmpty()){
					root = (BSTNode<T>) root.getLeft();
				} else {
					root = (BSTNode<T>) root.getRight();
				}
			}
			rebalanceUp((BSTNode<T>) node.getParent());
		} else {
			BSTNode<T> sucessor = sucessor(node.getData());
			node.setData(sucessor.getData());
			remove(sucessor);
		}
	}
	private boolean nodeHasOneChild(BSTNode<T> node){
		boolean resp = false;
		if(!node.getLeft().isEmpty() && node.getRight().isEmpty()){
			resp = true;
		} else if(!node.getRight().isEmpty() && node.getLeft().isEmpty()){
			resp = true;
		}
		return resp;
	}
	private boolean nodeIsLeftChild(BSTNode<T> node){
		return (node.getParent().getData().compareTo(node.getData()) > 0);
	}
}
