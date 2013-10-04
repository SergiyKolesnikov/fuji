package test;

public class NullAssignTest {
	
	void m1() {
		Object obj = new Object();
		if (obj == null) { // keep obj alive
		}
		obj = null; // null assign not dead
	}

	void m2() {
		int[] a = new int[2];
		a = null; // null assign not dead
	}

}
