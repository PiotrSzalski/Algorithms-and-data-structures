import java.util.Scanner;

public class Zadanie1 {
	public static void main(String[] args) {
		
		PriorityQueue<Integer,Integer> pq = new PriorityQueue<>();		
		
		Scanner input = new Scanner(System.in);
		int m = 0;
		m = input.nextInt();
		for(int i = 0; i < m; i++) {
			String operation = input.next();
			if(operation.equals("insert")) {
				int x = input.nextInt();
				int p = input.nextInt();
				if(p < 0) {
					System.err.println("Negative priority");
					i--;
					continue;
				}
				pq.insert(x, p);
			} else if(operation.equals("empty")) {
				System.out.println(pq.empty());
			} else if(operation.equals("top")) {
				if(pq.top() == null) {
					System.out.println("");
				} else {
					System.out.println(pq.top());
				}
 			} else if(operation.equals("pop")) {
				Integer x = pq.pop();
				if(x == null) {
					System.out.println("");
				} else {
					System.out.println(x);
				}
			} else if(operation.equals("priority")) {
				int x = input.nextInt();
				int p = input.nextInt();
				if(p < 0) {
					System.err.println("Negative priority");
					i--;
					continue;
				}
				pq.priority(x, p);
			} else if(operation.equals("print")) {
				pq.print();
			} else {
				System.out.println("Undefined operation");
				i--;
			}
		}
		input.close();
	}
}
