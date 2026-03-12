package adt.bst;

/**
 * 
 * Performs consistency validations within a BST instance
 * 
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class BSTVerifierImpl<T extends Comparable<T>> implements BSTVerifier<T> {
	
	private BSTImpl<T> bst;

	public BSTVerifierImpl(BST<T> bst) {
		this.bst = (BSTImpl<T>) bst;
	}
	
	private BSTImpl<T> getBSt() {
		return bst;
	}

	@Override
	public boolean isBST() {
		return isBST(this.getBSt().getRoot(), null, null);
	}

	private boolean isBST(BSTNode<T> node, T min, T max) {
		boolean resp = false;
		if (node.isEmpty()) {
			resp = true;
		} else if ((min == null || node.getData().compareTo(min) > 0) && 
			(max == null || node.getData().compareTo(max) < 0)) {
		
			boolean leftIsBST = isBST((BSTNode<T>) node.getLeft(), min, node.getData());
			boolean rightIsBST = isBST((BSTNode<T>) node.getRight(), node.getData(), max);
			resp = leftIsBST && rightIsBST;
		}
		return resp;
	}
}
