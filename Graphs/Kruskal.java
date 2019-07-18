public class Kruskal {
	private Graph g;
	private double weightMTS;
	
	public Kruskal(Graph g) {
		this.g = g;
	}
	
	public void kruskal() {
		int edges = 0;
		PriorityQueue<Integer,Double> pq = new PriorityQueue<>();
		for(int i = 0; i < g.getEdgeNumber(); i++) {
			pq.insert(i, g.getEdge(i).getWeight());
		}
		for(int i = 1; i <= g.getVertexNumber(); i++) {
			makeSet(g.getVertex(i));
		}
		while(!pq.empty() && edges != g.getVertexNumber() - 1) {
			Integer u = pq.pop(); 
			Edge e = g.getEdge(u);
			if(find(e.getFrom()) != find(e.getTo())) {
				if(e.getFrom().getId() < e.getTo().getId()) {
					System.out.println(e.getFrom().getId()+" "+e.getTo().getId()+" "+e.getWeight());
				} else {
					System.out.println(e.getTo().getId()+" "+e.getFrom().getId()+" "+e.getWeight());
				}
				edges++;
				weightMTS += e.getWeight();
				union(e.getFrom(),e.getTo());
			}
		}
	}
	
	private void makeSet(Vertex vertex) {
		vertex.setParent(vertex);
		vertex.setRank(0);
	}
	
	private Vertex find(Vertex vertex) {
		if(vertex == vertex.getParent()) {
			return vertex;
		}
		return find(vertex.getParent());
	}
	
	private void union(Vertex vertexA, Vertex vertexB) {
        Vertex rootA = find(vertexA);
        Vertex rootB = find(vertexB);
        if (rootA.equals(rootB))
            return;
        if (rootA.getRank() > rootB.getRank() ) {
            rootB.setParent(rootA);
        } else {
            rootA.setParent(rootB);
            if (rootA.getRank() == rootB.getRank()) {
            	rootB.setRank(rootB.getRank() + 1);
            }
        }
    }
	
	public void printWeight() {
		System.out.println(weightMTS);
	}
}
