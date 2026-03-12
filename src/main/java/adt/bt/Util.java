package adt.bt;

import adt.bst.BSTNode;

public class Util {


	/**
	 * A rotacao a esquerda em node deve subir e retornar seu filho a direita
	 * @param node
	 * @return - noh que se tornou a nova raiz
	 */
	public static <T extends Comparable<T>> BSTNode<T> leftRotation(BSTNode<T> node) {
		BSTNode<T> pivot = node;
		if (!node.isEmpty() && !node.getRight().isEmpty()) {
			pivot = (BSTNode<T>) node.getRight();
			node.right = pivot.getLeft();
			if (!pivot.getLeft().isEmpty()) {
				pivot.getLeft().parent = node;
			}
			pivot.parent = node.parent;
			pivot.left = node;
			node.parent = pivot;
		}
		return pivot;
	}

	/**
	 * A rotacao a direita em node deve subir e retornar seu filho a esquerda
	 * @param node
	 * @return noh que se tornou a nova raiz
	 */
	public static <T extends Comparable<T>> BSTNode<T> rightRotation(BSTNode<T> node) {
		BSTNode<T> pivot = node;
		if (!node.isEmpty() && !node.getLeft().isEmpty()) {
			pivot = (BSTNode<T>) node.getLeft();
			node.left = pivot.getRight();
			if (!pivot.getRight().isEmpty()) {
				pivot.getRight().parent = node;
			}
			pivot.parent = node.parent;
			pivot.right = node;
			node.parent = pivot;
		}
		return pivot;
	}

	public static <T extends Comparable<T>> T[] makeArrayOfComparable(int size) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) new Comparable[size];
		return array;
	}
}
