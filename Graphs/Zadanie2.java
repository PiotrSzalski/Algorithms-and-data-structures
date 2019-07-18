import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Zadanie2 {

	public static void main(String[] args) throws FileNotFoundException {
		
		boolean file = true; 
		Scanner input;
		if(file) {
			input = new Scanner(new File("Dijkstra.txt"));
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
			double w = input.nextDouble();
			Edge e = new Edge(g.getVertex(u),g.getVertex(v),w);
			g.getNeighbours(u).add(e);
		}
		int start = input.nextInt();
		long startTime = System.nanoTime();
		g.dijkstra(start);
		long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;
		g.printPath();
		System.err.println((double)totalTime/1000000);
		input.close();
	}

}
