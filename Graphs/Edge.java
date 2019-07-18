public class Edge {
	private Vertex from;
	private Vertex to;
	private double weight;
	
	public Edge(Vertex f, Vertex t, double w) {
		from = f;
		to = t;
		weight = w;
	}
	
	public Edge(Vertex f, Vertex t) {
		from = f;
		to = t;
	}

	public Vertex getFrom() {
		return from;
	}

	public void setFrom(Vertex from) {
		this.from = from;
	}

	public Vertex getTo() {
		return to;
	}

	public void setTo(Vertex to) {
		this.to = to;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
}