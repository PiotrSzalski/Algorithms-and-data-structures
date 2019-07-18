import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Zadanie4 {

	public static void main(String[] args) throws FileNotFoundException {
		boolean file = true; 
		Scanner input;
		if(file) {
			input = new Scanner(new File("Kosaraju.txt"));
		} else {
			input = new Scanner(System.in);
		}
		int n = input.nextInt();
		int m = input.nextInt();
		Graph g = new Graph(n);
		for(int i = 1; i <= n; i++) {
			g.addVertex(new Vertex(i));
		}
		for(int i = 0; i < m; i++) {
			int u = input.nextInt();
			int v = input.nextInt();
			Edge e = new Edge(g.getVertex(u),g.getVertex(v));
			g.getNeighbours(u).add(e);
		}
		StronglyConnectedComponent scc = new StronglyConnectedComponent(g);
		long startTime = System.nanoTime();
		scc.kosaraju();
		long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;
		System.err.println((double)totalTime/1000000);
		input.close();	
	}

}
