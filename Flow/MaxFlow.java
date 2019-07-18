import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class MaxFlow {
	
	private ArrayList<ArrayList<Edge>> graph;
	private int k;
	private int vertexes;
	private boolean[] visited;
	private int[] parent;
	private int maxFlow;
	private int paths;
	private Random r;
	
	public MaxFlow(int k) {
		this.k = k;
		this.vertexes = (int)Math.pow(2, k);
		graph = new ArrayList<ArrayList<Edge>>(vertexes);
		for(int i = 0; i < vertexes; i++) {
			graph.add(new ArrayList<Edge>(k));
		}
		this.visited = new boolean[vertexes];
		this.parent = new int[vertexes];
		r = new Random();
	}	
	
	public void generateHyperCube() {
		for(int i = 0; i < vertexes; i++) {
			String fromBin = Integer.toBinaryString(i);
			while(fromBin.length() != k) {
				fromBin = "0" + fromBin;
			}
			for(int j = 0; j < k; j++) {
				String toBin;
				if(fromBin.charAt(j) == '1') {
					toBin = fromBin.substring(0,j)+'0'+fromBin.substring(j+1);
				} else {
					toBin = fromBin.substring(0,j)+'1'+fromBin.substring(j+1);
				}
				int to = Integer.parseInt(toBin, 2);
				int weight = 0;
				int weightFrom = hammingWeight(fromBin);
				int weightTo = hammingWeight(toBin);
				if(weightFrom > weightTo) {
					weight = 0;
				} else if(weightFrom < weightTo) {
					int l = 0;
					if(weightFrom > l) {
						l = weightFrom;
					}
					if(weightTo > l) {
						l = weightTo;
					}
					if(k - weightFrom > l) {
						l = k - weightFrom;
					}
					if(k - weightTo > l) {
						l = k - weightTo;
					}
					weight = r.nextInt((int) Math.pow(2, l)) + 1;
				} else {
					System.err.println("Error HyperCube");
					System.exit(0);
				}
				graph.get(i).add(new Edge(to,weight));
			}
		}
	}
	
	public void countMaxFlow(int s, int t) {	
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
                for (int i = 0; i < graph.get(current).size(); i++) {
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
			paths++;
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
        	maxFlow += graph.get(s).get(i).getFlow();
        }
	}
	
	public int getMaxFlow() {
		return maxFlow;
	}
	
	public int getPaths() {
		return paths;
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
				+ "printf \"Max Flow: %g\\n\\n\", flow; \n \n"
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
	
	private int hammingWeight(String bin) {
		int count = 0;
		for(int i = 0; i < bin.length(); i++) {
			if(bin.charAt(i) == '1') {
				count++;
			} 
		}
		return count;
	}
}

class Edge {
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
