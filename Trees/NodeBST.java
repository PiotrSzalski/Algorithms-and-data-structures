public class NodeBST {
	
	private String value;
	private NodeBST left;
	private NodeBST right;
	
	public NodeBST(String value) {
		this.value = value;
		left = null;
		right = null;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public NodeBST getLeft() {
		return left;
	}
	public void setLeft(NodeBST left) {
		this.left = left;
	}
	public NodeBST getRight() {
		return right;
	}
	public void setRight(NodeBST right) {
		this.right = right;
	}
}
