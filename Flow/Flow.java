import java.io.File;

public class Flow { 

	public static void main(String[] args) {
			
		long timeStart = System.nanoTime();
		File f = null;
		boolean glpk = false;
		int k = 0;
		
		if(args.length %2 != 0) {
			System.err.println("Incorrect number of parameters");
			System.exit(0);
		}
		
		for(int i = 0; i < args.length; i++) {
			if(args[i].equals("--size")) {
				i++;
				k = Integer.parseInt(args[i]);
			} else if(args[i].equals("--glpk")) {
				i++;
				glpk = true;
				f = new File(args[i]);
			}
		}
		
		if(k < 1 || k >16) {
			System.err.println("Incorrect --size parameter");
			System.exit(0);
		}
		
		MaxFlow g = new MaxFlow(k);
		g.generateHyperCube();
		if(glpk) {
			g.generateGlpk(f);
		}
		g.countMaxFlow(0, (int)Math.pow(2, k)-1);
		long timeEnd = System.nanoTime();
		System.out.println(g.getMaxFlow());
		System.err.println((double)(timeEnd-timeStart)/1000000);
		System.err.println(g.getPaths());
	}
}
