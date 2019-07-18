import java.io.File;

public class Matching {

	public static void main(String[] args) {
		
		long timeStart = System.nanoTime();
		File f = null;
		boolean glpk = false;
		int k = 0;
		int i = 0;
		
		if(args.length %2 != 0) {
			System.err.println("Incorrect number of parameters");
			System.exit(0);
		}
		
		for(int j = 0; j < args.length; j++) {
			if(args[j].equals("--size")) {
				j++;
				k = Integer.parseInt(args[j]);
			} else if(args[j].equals("--glpk")) {
				j++;
				glpk = true;
				f = new File(args[j]);
			} else if(args[j].equals("--degree")) {
				j++;
				i = Integer.parseInt(args[j]);
			}
		}
		
		if(k < 1 || k >16) {
			System.err.println("Incorrect --size parameter");
			System.exit(0);
		}
		if(i < 1 || i > k) {
			System.err.println("Incorrect --degree parameter");
			System.exit(0);
		}
		
		MaxMatching mm = new MaxMatching(k,i);
		mm.generateBipartiteGraph();
		if(glpk) {
			mm.generateGlpk(f);
		}
		mm.countMaxMatching();
		long timeEnd = System.nanoTime();
		System.out.println(mm.getMaxMatching());
		System.err.println((double)(timeEnd - timeStart)/1000000);
	}
}
