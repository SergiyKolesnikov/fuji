package test;

import java.util.*;

public class ArrayTest {

	void m1() {
		int[] a = new int[2];
		int[] c = new int[2];
		c[0] = 2;
		int b = 4;
		a[b++] = b;
		a = new int [3];
		a[b-2] = c[0] + c[0];
		int d = a[0] + a[1];
		int[] e = new int[10];
	}

	int[][] m2() {
		int b = 2;
		int[][] c = new int[10][2];
		c[2][0] = b;
		c[4][1] = b + c[3][0];
		return c;
	}
	

	ArrayList<Expr> stack = new ArrayList<Expr>();
	int height = 0;
	public Expr[] m3() {
    	Expr top = (Expr)stack.remove(stack.size() - 1);
	    Expr[] a;
    	Type type = top.type();
	    if(type.isWide()) {
	      a = new Expr[1];
    	  a[0] = top;
    	}
    	else {
    	  a = new Expr[2];
    	  a[0] = (Expr)stack.remove(stack.size() - 1);
    	  a[1] = top;
    	}
    	height -= 2;
    	return a;
  	}
}

class Type {
	boolean isWide() {
		return true;
	}
}

class Expr {
	Type type() {
		return new Type();
	}
}
