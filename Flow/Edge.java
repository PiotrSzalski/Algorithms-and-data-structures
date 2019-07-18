public class Edge {
	private int to;
	private int capacity;
	private int flow;
	
	public Edge(int to, int capacity) {
		this.to = to;
		this.capacity = capacity;
		this.flow = 0;
	}
	
	public int getTo() {
		return to;
	}
	public void setTo(int to) {
		this.to = to;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getFlow() {
		return flow;
	}
	public void setFlow(int flow) {
		this.flow = flow;
	}
}