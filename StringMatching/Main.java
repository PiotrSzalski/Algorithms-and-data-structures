package Algrithms;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in,"utf-8");
		String alph = "ÆQRT";
		String text = "QÆQÆÆQÆÆÆQÆÆÆÆQÆQÆQÆQRÆQR";
		String[] pattern = {"T","ÆT","ÆQ","ÆÆ","QÆQ","QÆQÆ"};
		
		for(int i = 0; i < pattern.length; i++) {
			Automata.match(pattern[i], alph, text);
			KMP.match(pattern[i], text);
		}
	}
}
