public class Prim {
	
	private Graph g;
	private double weightMTS;
	
	public Prim(Graph g) {
		this.g = g;
	}
	
	public void prim() {
		PriorityQueue<Integer,Double> q = new PriorityQueue<>();
		g.getVertex(1).setDist(0);
		boolean[] visited = new boolean[g.getVertexNumber()];
		for(int i = 0; i < visited.length; i++) {
			visited[i] = false;
			q.insert(i+1, g.getVertex(i+1).getDist());
		}	
		while(!q.empty()) {
			Integer u = q.pop();
			visited[u-1] = true;
			for(int i = 0; i < g.getNeighbours(u).size(); i++) {
				Edge e = g.getNeighbours(u).get(i);
				if(!visited[e.getTo().getId()-1] && e.getTo().getDist() > e.getWeight()) {
					e.getTo().setDist(g.getNeighbours(u).get(i).getWeight());
					e.getTo().setPrev(e.getFrom());
					q.priority(e.getTo().getId(), e.getTo().getDist());
				}
			}
		}
	}
	
	public void printMTS() {
		for(int i = 1; i <= g.getVertexNumber(); i++) {
			if(g.getVertex(i).getPrev() != null) {
				if(g.getVertex(i).getId() < g.getVertex(i).getPrev().getId()) {
					System.out.println(g.getVertex(i).getId()+" "+g.getVertex(i).getPrev().getId()+" "+g.getVertex(i).getDist());
				} else {
					System.out.println(g.getVertex(i).getPrev().getId()+" "+g.getVertex(i).getId()+" "+g.getVertex(i).getDist());
				}
				weightMTS += g.getVertex(i).getDist();
			}
		}
	}
	
	public void printWeight() {
		System.out.println(weightMTS);
	}
	
}
