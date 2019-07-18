import java.util.ArrayList;

public class StronglyConnectedComponent {
	
	private Graph g;
	private boolean[] visited;
	private int time;
	private Vertex[] last;

	public StronglyConnectedComponent(Graph g) {
		this.g = g;
		last = new Vertex[g.getVertexNumber()];
	}

	public void kosaraju() {
		time = 0;
		visited = new boolean[g.getVertexNumber()];
		for(int i = 0; i < visited.length; i++) {
			visited[i] = false;
		}
		for(int i = 0; i < g.getVertexNumber(); i++) {
			if(!visited[i]) {
				DFS(g.getVertex(i+1));
			}
		}
		reverseGraph();
		for(int i = 0; i < visited.length; i++) {
			visited[i] = false;
		}
		for(int i = last.length - 1; i >= 0; i--) {
			if(!visited[last[i].getId()-1]) {
				System.out.print(last[i].getId()+" ");
				DFS2(last[i]);
				System.out.println("");
			}
		}
	}
	private void DFS(Vertex v) {
		visited[v.getId()-1] = true;
		ArrayList<Edge> al = g.getNeighbours(v.getId());
		for(int i = 0; i < al.size(); i++) {
			if(!visited[al.get(i).getTo().getId()-1]) {
				DFS(al.get(i).getTo());
			}
		}
		last[time] = v;
		time++;
	}
	
	private void DFS2(Vertex v) {
		visited[v.getId()-1] = true;
		ArrayList<Edge> al = g.getNeighbours(v.getId());
		for(int i = 0; i < al.size(); i++) {
			if(!visited[al.get(i).getTo().getId()-1]) {
				System.out.print(al.get(i).getTo().getId()+" ");
				DFS2(al.get(i).getTo());
			}
		}
	}
	
	public void reverseGraph() {
		ArrayList<ArrayList<Edge>> neighbours2 = new ArrayList<ArrayList<Edge>>();
		for(int i = 0; i < g.getVertexNumber(); i++) {
			neighbours2.add(new ArrayList<Edge>());
		}
		for(int i = 1; i <= g.getVertexNumber(); i++) {
			ArrayList<Edge> al = g.getNeighbours(i);
			for(int j = 0; j < al.size(); j++) {
				neighbours2.get(al.get(j).getTo().getId()-1).add(new Edge(al.get(j).getTo(),al.get(j).getFrom()));
			}
		}
		g.setNeighbours(neighbours2);
	}
}
