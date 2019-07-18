import java.io.File;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		if(args.length != 2 || !args[0].equals("--type")) {
			System.out.println("Incorrect startup parameters");
			System.exit(0);
		}
		
		Tree tree = null;
		if(args[1].equals("bst")) {
			tree = new BinarySearchTree();
		} else if(args[1].equals("rbt")) {
			tree = new RedBlackTree();
		} else if(args[1].equals("st")) {
			tree = new SplayTree();
		} else {
			System.out.println("Incorrect startup parameters");
			System.exit(0);
		}
		
		Scanner input = new Scanner(System.in);
		int numberOperation = input.nextInt();
		for(int i = 0; i < numberOperation; i++) {
			String operation = input.next();
			String s;
			if(operation.equals("insert")) {
				s = input.next();
				tree.insert(s);
			} else if(operation.equals("delete")) {
				s = input.next();
				tree.delete(s);
			} else if(operation.equals("search")) {
				s = input.next();
				if(tree.search(s)) {
					System.out.println("1");
				} else {
					System.out.println("0");
				}
			} else if(operation.equals("load")) {
				s = input.next();
				File f = new File(s);
				tree.load(f);
			} else if(operation.equals("inorder")) {
				tree.inorder();
			} else {
				input.next();
				System.out.println("Unrecognized operation");
			}
		}
		input.close();
		long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;
		System.err.println("Total time: "+(double)totalTime/1000000);
		System.err.println("Number of insert: "+tree.getInserion());
		System.err.println("Number of delete: "+tree.getRemoval());
		System.err.println("Number of search: "+tree.getSearch());
		System.err.println("Number of load: "+tree.getLoad());
		System.err.println("Number of inorder: "+tree.getInorder());
		System.err.println("Maximum number of elements: "+tree.getMaxInserted());
		System.err.println("Number of elements in structure at the end: "+tree.getInserted());
		System.err.println("Number of comparisons: "+tree.getComparisons());
		System.err.println("Number of modifications: "+tree.getMods());
	}

}
