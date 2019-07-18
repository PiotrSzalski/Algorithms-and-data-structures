import java.util.ArrayList;

public class Graph {
	private Vertex[] vertexArray;
	private Edge[] edgesArray;
	private int addedVer = 0;
	private int addedEdg = 0;
	private ArrayList<ArrayList<Edge>> neighbours;
	
	public Graph(int n, int m) {
		vertexArray = new Vertex[n];
		edgesArray = new Edge[m];
		neighbours = new ArrayList<ArrayList<Edge>>();
		for(int i = 0; i < n; i++) {
			neighbours.add(new ArrayList<Edge>());
		}
	}
	
	public Graph(int n) {
		vertexArray = new Vertex[n];
		neighbours = new ArrayList<ArrayList<Edge>>();
		for(int i = 0; i < n; i++) {
			neighbours.add(new ArrayList<Edge>());
		}
	}
	
	public void addVertex(Vertex v) {
		vertexArray[addedVer] = v;
		addedVer++;
	}
	
	public void addEdge(Edge e) {
		edgesArray[addedEdg] = e;
		addedEdg++;
	}
	
	public Vertex getVertex(int i) {
		return vertexArray[i-1];
	}
	
	public Edge getEdge(int i) {
		return edgesArray[i];
	}
	
	public int getVertexNumber() {
		return vertexArray.length;
	}
	
	public int getEdgeNumber() {
		return edgesArray.length;
	}
	
	public Vertex[] getVertexArray() {
		return vertexArray;
	}
	
	public void setVertexArray(Vertex[] va) {
		vertexArray = va;
	}

	public void dijkstra(int id) {
		getVertex(id).setDist(0);
		PriorityQueue<Integer,Double> q = new PriorityQueue<>();	
		for(int i = 1; i <= vertexArray.length; i++) {
			q.insert(i, getVertex(i).getDist());
		}
		while(!q.empty()) {
			Integer u = q.pop();
			double dist = getVertex(u).getDist();
			for(int i = 0; i < getNeighbours(u).size(); i++) {
				Vertex to = getNeighbours(u).get(i).getTo();
				if(to.getDist() > dist + getNeighbours(u).get(i).getWeight()) {
					to.setDist(dist + getNeighbours(u).get(i).getWeight());
					to.setPrev(getVertex(u));
					q.priority(to.getId(), to.getDist());
				}
			}
		}
	}
	
	public void printPath() {
		for(int i = 1; i <= vertexArray.length; i++) {
			if(getVertex(i).getDist() == Double.MAX_VALUE) {
				System.out.println(i+" No path");
			} else {
				System.out.println(i+" "+getVertex(i).getDist());
				String path = "";
				Vertex v = getVertex(i);
				while(v.getPrev() != null) {
					path = v.getId() + "(" + (v.getDist() - v.getPrev().getDist()) + ") -> " + path;
					v = v.getPrev();
				}
				path = v.getId() + "(" + v.getDist() + ") -> " + path;
				path = path.substring(0, path.length()-3);
				System.err.println(path);
				path = "";
			}
		}
	}

	public ArrayList<Edge> getNeighbours(int i) {
		return neighbours.get(i-1);
	}

	public void setNeighbours(ArrayList<ArrayList<Edge>> neighbours) {
		this.neighbours = neighbours;
	}
}