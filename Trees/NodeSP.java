public class NodeSP {
	
	private String value;
	private NodeSP left;
	private NodeSP right;
	private NodeSP parent;
	
	public NodeSP(String value) {
		this.value = value;
		left = null;
		right = null;
		parent = null;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public NodeSP getLeft() {
		return left;
	}
	public void setLeft(NodeSP left) {
		this.left = left;
	}
	public NodeSP getRight() {
		return right;
	}
	public void setRight(NodeSP right) {
		this.right = right;
	}

	public NodeSP getParent() {
		return parent;
	}

	public void setParent(NodeSP parent) {
		this.parent = parent;
	}
}

