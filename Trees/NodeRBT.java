public class NodeRBT {
	private NodeRBT left;
	private NodeRBT right;
	private String value;
	private boolean red;

	private NodeRBT parent;
	
	public NodeRBT(String v) {
		left = null;
		right = null;
		value = v;
		red = true;
	}
	
	public NodeRBT getLeft() {
		return left;
	}
	public void setLeft(NodeRBT left) {
		this.left = left;
	}
	public NodeRBT getRight() {
		return right;
	}
	public void setRight(NodeRBT right) {
		this.right = right;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isRed() {
		return red;
	}
	public void setRed(boolean red) {
		this.red = red;
	}
	public void setParent(NodeRBT n) {
		this.parent = n;
	}
	public NodeRBT getParent() {
		return parent;
	}
}
