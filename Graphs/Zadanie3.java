import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Zadanie3 {
	public static void main(String[] args) throws FileNotFoundException {
		
		boolean file = true;
		boolean kruskal = true;
		
		if(args.length == 1 && args[0].equals("-p")) {
			kruskal = false;
		} else if (args.length == 1 && args[0].equals("-k")) {
			kruskal = true;
		} else {
			System.err.println("Choose algorithm '-p' Prim or '-k' Kruskal");
			System.exit(0);
		}
		
		Scanner input;
		if(file) {
			if(kruskal) {
				input = new Scanner(new File("Kruskal.txt"));
			} else {
				input = new Scanner(new File("Prim.txt"));
			}
		} else {
			input = new Scanner(System.in);
		}
		int n = input.nextInt();
		int m = input.nextInt();
		Graph g = new Graph(n,m);
		for(int i = 1; i <= n; i++) {
			g.addVertex(new Vertex(i));
		}
		for(int i = 0; i < m; i++) {
			int u = input.nextInt();
			int v = input.nextInt();
			double w = input.nextDouble();
			Edge e1 = new Edge(g.getVertex(u),g.getVertex(v),w);
			if(!kruskal) {
				Edge e2 = new Edge(g.getVertex(v),g.getVertex(u),w);
				g.getNeighbours(u).add(e1);
				g.getNeighbours(v).add(e2);
			}
			g.addEdge(e1);
		}
		if(kruskal) {
			Kruskal k = new Kruskal(g);
			System.out.println("Kruskal");
			k.kruskal();
			k.printWeight();
		} else {
			Prim p = new Prim(g);
			System.out.println("Prim");
			p.prim();
			p.printMTS();
			p.printWeight();
		}
		input.close();	
	}
}
