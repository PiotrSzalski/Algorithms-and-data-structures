import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class MaxMatching {
	
	private ArrayList<ArrayList<Edge>> graph;
	private int k,i;
	private int vertexes;
	private boolean[] visited;
	private int[] parent;
	private int maxMatching;
	private Random r;
	
	public MaxMatching(int k, int i) {
		r = new Random();
		this.k = k;
		this.i = i;
		this.vertexes = 2 + (int) Math.pow(2, k+1);
		graph = new ArrayList<ArrayList<Edge>>(vertexes);
		for(int j = 0; j < vertexes; j++) {
			graph.add(new ArrayList<Edge>());
		}
		this.visited = new boolean[vertexes];
		this.parent = new int[vertexes];
	}	
	
	public void generateBipartiteGraph() {
		for(int j = 1; j <= (int) Math.pow(2, k); j++) {
			graph.get(0).add(new Edge(j,1));
			graph.get(j).add(new Edge(0,0));
			
			ArrayList<Integer> random = new ArrayList<>(i);
			while(random.size() != i) {
				int ran = (int) Math.pow(2, k) + r.nextInt((int) Math.pow(2,k)) + 1;
				if(!random.contains(ran)) {
					random.add(ran);
					graph.get(j).add(new Edge(ran,1));
					graph.get(ran).add(new Edge(j,0));
				}
			}
		}
		for(int j = (int) Math.pow(2,k) + 1 ; j < vertexes - 1; j++) {
			graph.get(j).add(new Edge(vertexes - 1,1));
			graph.get(vertexes - 1).add(new Edge(j,0));
		}
	}
	
	public void countMaxMatching() {	
		int s = 0;
		int t = 1 + (int) Math.pow(2, k+1);
 		while(true) {
			Queue<Integer> queue = new ArrayDeque<Integer>();
			queue.add(s);
			
			for(int i = 0; i < visited.length; i++) {
				visited[i] = false;
			}
			visited[s] = true;
			
			boolean reachTarget = false;
			int current;
			while (!queue.isEmpty()) {
				Integer c = queue.remove();
                current = c;
                if (current == t) {
                	reachTarget = true;
                    break;
                }
                for (int i = 0; i < graph.get(c).size(); i++) {
                	int to = graph.get(current).get(i).getTo();
                    if (!visited[to] && getCapacity(current,to) > getFlow(current,to)) {
                        visited[to] = true;
                        queue.add(to);
                        parent[to] = current;
                    }
                }
            }
			if(!reachTarget) {
				break;
			}
			int minCapacity = getCapacity(parent[t],t) - getFlow(parent[t],t);
			for (int i = t; i != s; i = parent[i]) {
				minCapacity = Math.min(minCapacity, getCapacity(parent[i],i) - getFlow(parent[i],i));
			}
			for (int i = t; i != s; i = parent[i]) {
				setFlow(parent[i], i, getFlow(parent[i],i) + minCapacity);
				setFlow(i, parent[i], getFlow(i,parent[i]) - minCapacity);
            }
		}
        for (int i = 0; i < graph.get(s).size(); i++) {
        	maxMatching += graph.get(s).get(i).getFlow();
        }
	}
	
	public int getMaxMatching() {
		return maxMatching;
	}
	
	public void generateGlpk(File f) {
		PrintWriter output = null;
		try {
			output = new PrintWriter(f);
		} catch (FileNotFoundException e) {
			System.err.println("Can not generate glpk");
		}
		output.println("/* number of nodes */ \n"
				+ "param n, integer, >= 2; \n"
				+ "/* set of nodes */ \n"
				+ "set V, default {0..(n-1)}; \n"
				+ "/* set of edges */ \n"
				+ "set E, within V cross V; \n"
				+ "/* c[i,j] - capacity of edge (i,j) */ \n"
				+ "param c{(i,j) in E}, > 0; \n"
				+ "/* source */ \n"
				+ "param s, symbolic, in V, default 0; \n"
				+ "/* target */ \n"
				+ "param t, symbolic, in V, != s, default (n-1); \n"
				+ "/* f[i,j] - flow through edge (i,j) */ \n"
				+ "var f{(i,j) in E}, >= 0, <= c[i,j]; \n"
				+ "/* total flow from s to t */ \n"
				+ "var flow, >= 0; \n"
				+ "/* node[i] - constraint for node i */ \n"
				+ "s.t. node{i in V}: \n"
				+ "/* sum of flow into node must be equal to sum of flow from node */ \n"
				+ "sum{(j,i) in E} f[j,i] + (if i = s then flow) = sum{(i,j) in E} f[i,j] + (if i = t then flow); \n"
				+ "/* maximize the total flow */ \n"
				+ "maximize obj: flow; \n"
				+ "solve; \n"
				+ "printf \"\\n\"; \n"
				+ "printf \"Max Matching: %g\\n\\n\", flow; \n \n"
				+ "data; \n"
				+ "param n := "+vertexes+"; \n"
				+ "param : E : c :=");
		
		for(int i = 0; i < graph.size(); i++) {
			for(int j = 0; j < graph.get(i).size(); j++) {
				if(graph.get(i).get(j).getCapacity() != 0) {
					output.println(i+" "+graph.get(i).get(j).getTo()+" "+graph.get(i).get(j).getCapacity());
				}
			}
		}
		output.println(";end;");
		output.close();
	}
	
	private int getCapacity(int from, int to) {
		for(int i = 0; i < graph.get(from).size(); i++) {
			if(graph.get(from).get(i).getTo() == to) {
				return graph.get(from).get(i).getCapacity();
			}
		}
		System.out.println("Error getCapacity");
		System.exit(0);
		return 0;
	}
	private int getFlow(int from, int to) {
		for(int i = 0; i < graph.get(from).size(); i++) {
			if(graph.get(from).get(i).getTo() == to) {
				return graph.get(from).get(i).getFlow();
			}
		}
		System.out.println("Error getFlow");
		System.exit(0);
		return 0;
	}
	private void setFlow(int from, int to,int flow) {
		for(int i = 0; i < graph.get(from).size(); i++) {
			if(graph.get(from).get(i).getTo() == to) {
				graph.get(from).get(i).setFlow(flow);
				return;
			}
		}
		System.out.println("Error setFlow");
		System.exit(0);
	}
}

