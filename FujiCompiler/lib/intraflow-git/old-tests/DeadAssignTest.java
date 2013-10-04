package test;

import java.util.*;
import java.awt.event.*;
import java.io.*;

public class DeadAssignTest {


	void m0(A a) {
		int i = 0;
		A o = a;
		o.f = 3;
	}

	boolean saveText = false;
	boolean FIELD = true;

	void m1(boolean param) {
		if (param == FIELD) {
			System.out.println("test");
		}
    	boolean oldsaveText = saveText;
	    saveText = saveText && b();
	    m2("aaa");
	    saveText = oldsaveText;
	}

	boolean b() {
		return false;
	}
	void m2(String s) {
		System.out.println(s);
	}

	class A {
		int f = 0;
	}


/*
    String sup = null;
    if(grammar.superClass != null) {
      sup = grammar.superClass;
    }
    else {
      sup = grammar.getSuperClass();
      if(sup.lastIndexOf('.') != -1) 
        sup = sup.substring(sup.lastIndexOf('.') + 1);
      sup = namespaceAntlr + sup;

	This is a good example of when the declaration will not be considered
	dead because it is used in an expression. The If-statement is dead given
	that all stmts in its "then" and "else" branch are dead. 
*/


	void m3() {

		boolean b = false;
	    String sup = null;
	    if(b) {
	      sup = "test";
	    }
	    else {
	      sup = "test2";
	      if(sup.lastIndexOf('.') != -1) 
	        sup = sup.substring(sup.lastIndexOf('.') + 1);   // NOT DEAD
	      sup = "test3" + sup;
	    }

	}

	void m4() {
		String s = "";
		String t = "";
		System.out.println(s + t);
		s = t = null; // DEAD t, not s
		System.out.println(s);
	}

	void m5() {
		int a = 0; // DEAD assign but used variable
		a = 2;

		System.out.println(""+a);
	}

	void m6() {
		boolean b = true;
		Class c = null; // Not dead
		try {
			c = Class.forName("a");
		} catch (Exception e) {
			if (b) 
				c = null;
		}
		if (c == null) 
			return;
		try {
			String s = c.getName();
		} catch (Exception e) {
		}
	}


	// Example of the assign with method access with throw on the RH
	void m7() {
		boolean b = false;
	    String name = null;
	    try {
			name = Class.forName("a").getName();;
      		{
        		_loop227:
				do {
    	        if(b) {
    	          m7();
    	        }
    	        else {
    	          break _loop227;
    	        }
    	      }while(true);
    	  }
    	}
    	catch (Exception ex) {
			m7();
    	}
	}

	// Example with anonymous class
	void m8() {
		final int a = 0; // Not dead
		ActionListener l = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				int b = 6;
				System.out.println(a+b); // read here
			}
		};
		System.out.println(l);
	}

	void m9() {
		String path = null;
	    try {
    	  path = new File("").getParent(); // Nothingh here throws IOException..!
			if (path != null) 
				path = new File(path).getCanonicalPath(); // This is the one throwing things
    	}
    	catch (IOException ioe) {
			System.err.println();    	  
    	}
    	if(path != null) {
 		     System.out.println(path);
 	   }
	}

	void m10() {
		int a = 0;
	    try {
    	  a = mm();
    	}
    	catch (NumberFormatException e) {
			System.err.println();    	  
    	}
    	if(a > 0) {
 		     System.out.println("");
 	   }
	}


	int mm() throws NumberFormatException 
	{
		if (true)
			throw new NumberFormatException();
		return 2;
	}

	void m11() {
	    String name = null;
	    try {
			name = Class.forName("a").getName();;
    	}
    	catch (Exception ex) {
			m7();
    	}
    	if(name != null) {
 		     System.out.println("");
 	   }

	}

	void m12() {
		int a = 0;
		a = mm();
	}




}
