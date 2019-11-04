package Algrithms;

import java.util.Scanner;

public class KMP {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in, "utf-8");
		
		String text = in.nextLine();
		String pattern = in.nextLine();
				
		match(pattern,text);
	}
	
	public static void match(String pattern, String text) {
		String result = "";
		int n = text.length();
		int m = pattern.length();
		int[] PF = computePF(pattern);
		int q = 0;
		result = "[";
		for(int i = 0; i < n; i++) {
			while (q > 0 && pattern.charAt(q) != text.charAt(i)) {
				q = PF[q-1];
			}
			if(pattern.charAt(q) == text.charAt(i)) {
				q++;
			}
			if(q == m) {
				result += (i-m+1+",");
				q = PF[q-1];
			}
		}
		if(result.charAt(result.length()-1) == ',') {
			result = result.substring(0,result.length()-1);
		}
		result += "]";
		System.out.println(result);
	}
	
	public static int[] computePF(String patttern) { 
        int m = patttern.length();
        int[] PF = new int[m];
        PF[0] = 0;
        int k = 0;
        for(int q = 1; q < m; q++) {
        	while (k > 0 && patttern.charAt(k) != patttern.charAt(q)) {
        		k = PF[k-1];
        	}
        	if(patttern.charAt(k) == patttern.charAt(q)) {
        		k++;
        	}
        	PF[q] = k;
        }
        return PF;
    }

}
