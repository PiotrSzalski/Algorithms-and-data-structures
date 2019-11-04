package Algrithms;

import java.util.Scanner;

public class Automata {

	public static void main( String[] args ) {
    	Scanner in = new Scanner(System.in,"utf-8");
    	
    	String alph = in.nextLine();
    	String text = in.nextLine();
    	String pattern = in.nextLine();
    	
    	match(pattern,alph,text);
    }
	
	public static void match(String pattern, String alph, String text) {
		int[][] TF = computeTF(pattern, alph);

		String result = "";
		int n = text.length();
		int m = pattern.length();
		int q = 0;
		result = "[";
		for (int i = 0; i < n; i++) {
			int l = 0;
			while (alph.charAt(l) != text.charAt(i)) {
				l++;
			}
			q = TF[q][l];
			if (q == m) {
				result += (i-m+1+",");
			}
		}
		if(result.charAt(result.length()-1) == ',') {
			result = result.substring(0,result.length()-1);
		}
		result += "]";
		System.out.println(result);
	}
    
    public static int[][] computeTF(String pattern, String alph) {
    	int m = pattern.length();
    	int[][] TF = new int[m + 1][alph.length()];
    	for (int q = 0; q <= m; q++) {
    		for (int i = 0; i < alph.length(); i++) {
    			int k = Math.min(m + 1, q + 2);
    			do {
    				k--;
    			} while (!isSuffix(pattern.substring(0, k),pattern.substring(0,q)+alph.charAt(i)));
    			TF[q][i] = k;
    		}
    	}
    	return TF;
    }
    
    public static boolean isSuffix(String x, String y) { 
        if (x.length() > y.length()) {
        	return false; 
        }
        for (int i = 0; i < x.length(); i++) {
        	if (x.charAt(x.length() - i - 1) != y.charAt(y.length() - i - 1)) {
        		return false;
        	}
        }
        return true; 
    }
}
