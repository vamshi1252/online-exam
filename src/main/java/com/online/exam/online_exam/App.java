package com.online.exam.online_exam;

/**
 * Hello world!
 *
 */

/* package whatever; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;

import org.apache.cxf.common.util.StringUtils;

/* Name of the class has to be "Main" only if the class is public. */

class Pair {
	public int x;
	public int y;
	
	private int z;

	Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return "(" + x + " ," + y + ")";
	}

}

class Paths {
	int X, Y;

	Paths(int X, int Y) {
		this.X = X;
		this.Y = Y;
	}

	@SuppressWarnings("unchecked")
	int recur(int x, int y, Stack st) {
		st.push(new Pair(x, y));
		int l = 0, r = 0;
		if (x == X && y == Y) {
			System.out.println(st);
			return 1;
		}
		if (x < X) {
			l = recur(x + 1, y, st);
			st.pop();
		}
		if (y < Y) {
			r = recur(x, y + 1, st);
			st.pop();
		}
		return l + r;
	}

	public List<String> permut(String s){
		List<String> permutations = new ArrayList<String>();
		//if(StringUtils.isEmpty(s)) return null;
		//if(s.length()==1) return s;
		System.out.println(s);
		if (s == null) { // error case
			return null;
			} else if (s.length() == 0) { // base case
			permutations.add(""); 
			return permutations;
			}
		
		char ch = s.charAt(0);
		List<String> ls = new ArrayList<String>();
		ls = permut(s.substring(1));
		System.out.println(ls);
		//if(ls==null) return null;
		for(String word : ls) {
			for(int j=0;j<=word.length();j++)
			  permutations.add(insertCharAt(word,ch,j));
		}
		return permutations;
	}
	
	private String insertCharAt(String word, char ch, int j) {
		String s1 = word.substring(0,j);
		String s2 = word.substring(j);
		return s1  + ch + s2;
	}
	
	
	
}

public class App {
	public static void main(String[] args) throws java.lang.Exception {
		// your code goes here

		Stack<Pair> st = new Stack<Pair>();
		// st.push(new Pair(0,0));
		Paths p = new Paths(0, 0);
		System.out.println(p.recur(0, 0, st));
		System.out.println(p.permut("abc"));

	}
}
