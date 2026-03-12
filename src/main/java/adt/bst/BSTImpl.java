package adt.bst;
import java.util.ArrayList;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		return height(root);
	}

	protected int height(BSTNode<T> node) {
		int resp = -1;
		if (!node.isEmpty()) {
			int heightLeft = height((BSTNode<T>) node.getLeft());
			int heightRight = height((BSTNode<T>) node.getRight());
			resp = 1 + Math.max(heightLeft, heightRight);
		}
		return resp;
	}

	@Override
	public BSTNode<T> search(T element) {
		return search(root, element);
	}

	private BSTNode<T> search(BSTNode<T> node, T element){
		BSTNode<T> resp = new BSTNode<>();
		if(!node.isEmpty()){
			if(element.compareTo(node.getData()) == 0){
				resp = node;
				} else if(element.compareTo(node.getData()) < 0){
					resp = search((BSTNode<T>) node.getLeft(), element);
				} else {
					resp = search((BSTNode<T>) node.getRight(), element);
				}
			}
		return resp;
	}

	@Override
	public void insert(T element) {
		if(element != null){
			insert(root, element);
		}
	}

	private void insert(BSTNode<T> node, T element){
		if(node.isEmpty()){
			node.setData(element);
			node.setLeft(new BSTNode<>());
			node.setRight(new BSTNode<>());
			node.getLeft().setParent(node);
			node.getRight().setParent(node);
		} else {
			if(element.compareTo(node.getData()) < 0){
				insert((BSTNode<T>)node.getLeft(), element);
			} else {
				insert((BSTNode<T>)node.getRight(), element);
			}
		}
	}

	@Override
	public BSTNode<T> maximum() {
		return maximum(root);
	}
	private BSTNode<T> maximum(BSTNode<T> node){
		BSTNode<T> resp = null;
		if(node.getRight().isEmpty()){
			resp = node;
		} else if(!node.getRight().isEmpty()){
			resp = maximum((BSTNode<T>) node.getRight());
		}
		return resp;
	}

	@Override
	public BSTNode<T> minimum() {
		return minimum(root);
	}
	private BSTNode<T> minimum(BSTNode<T> node){
		BSTNode<T> resp = null;
		if(node.getLeft().isEmpty()){
			resp = node;
		} else if (!node.getLeft().isEmpty()){
			resp = minimum((BSTNode<T>) node.getLeft());
		}
		return resp;
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> node = search(element);
		BSTNode<T> resp = null;
		if(!node.isEmpty()){
			if(!node.getRight().isEmpty()){
				resp = minimum((BSTNode<T>) node.getRight());
			} else {
				BSTNode<T> nodeAtual = node;
				BSTNode<T> parent = (BSTNode<T>) nodeAtual.getParent();
				while(parent != null && nodeAtual == parent.getRight()){
					nodeAtual = parent;
					parent = (BSTNode<T>) parent.getParent();
				}
				resp = parent;
			}
		}
		return resp;
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> node = this.search(element);
		BSTNode<T> resp = null;
		if(!node.isEmpty()){
			if(!node.getLeft().isEmpty()){
				resp = maximum((BSTNode<T>) node.getLeft());
			} else {
				BSTNode<T> nodeAtual = node;
				BSTNode<T> parent = (BSTNode<T>) node.getParent();
				while(parent != null && nodeAtual == parent.getLeft()){
					nodeAtual = parent;
					parent = (BSTNode<T>) parent.getParent();
				}
				resp = parent;
			}
		}
		return resp;
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

	@Override
	public T[] preOrder() {
		ArrayList<T> lista = new ArrayList<>();
		preOrder(root, lista);
		return lista.toArray((T[]) new Comparable[lista.size()]);
	}
	private void preOrder(BSTNode<T> node, ArrayList<T> lista){
		if(!node.isEmpty()){
			lista.add(node.getData());
			preOrder( (BSTNode<T>) node.getLeft(), lista);
			preOrder( (BSTNode<T>) node.getRight(), lista);
		}
	}

	@Override
	public T[] order() {
		ArrayList<T> lista = new ArrayList<>();
		order(root, lista);
		return lista.toArray((T[]) new Comparable[lista.size()]);
	}
	private void order(BSTNode<T> node, ArrayList<T> lista){
		if(!node.isEmpty()){
			order( (BSTNode<T>) node.getLeft(), lista);
			lista.add(node.getData());
			order((BSTNode<T>) node.getRight(), lista);
		}
	}

	@Override
	public T[] postOrder() {
		ArrayList<T> lista = new ArrayList<>();
		postOrder(root, lista);
		return lista.toArray((T[]) new Comparable[lista.size()]);
	}
	private void postOrder(BSTNode<T> node, ArrayList<T> lista){
		if(!node.isEmpty()){
			postOrder((BSTNode<T>) node.getLeft(), lista);
			postOrder((BSTNode<T>) node.getRight(), lista);
			lista.add(node.getData());
		}
	}

	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft())
					+ size((BSTNode<T>) node.getRight());
		}
		return result;
	}

}
