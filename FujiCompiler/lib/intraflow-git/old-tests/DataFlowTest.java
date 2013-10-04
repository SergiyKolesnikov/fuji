package test;

import java.util.*;

public class DataFlowTest {


	void m0() {
		int a = 0;
		a = 2;
		a = 3;
		int c = a + "".length();
	}

	// data flow through block
	void m1() {
		int a = 0;
		int b = 0;
		int c = 0;
		a++;
	}	

	// data flow through if
	void m2() {
		int a = 0;
		int b = 0;
		if (b > 0) {
			int c;
		} else {
			b++;		
		}
		a++;
	}

	// data flow through synchronized
	void m3() {
		int a = 0;
		synchronized (this) {
			int b;
		}
		a++;
	}

	// data flow through switch-case
	void m4() {
		int a = 0;
		int b = 0;
		switch(a) {
		case 0: break;
		case 2: b++;
		}
		a++;
	}

	// data flow through while
	void m5() {
		int a = 0;
		int b = 0;
		while (b > 0) {
			int c;
		}
		a++;
	}

	// data flow through do-while
	void m6() {
		int a = 0;
		int b = 0;
		do {
			int c;
		} while (b > 0);
		a++;
	}

	// data flow through for
	void m7() {
		int a = 0;
		for (int i = 0; i < 10; i++) {
			int b;
		}
		a++;
	}

	// data flow through break-label
	void m8() {
		int a = 0;
		outer:
		while (true) {
			break outer;
		}
		a++;
	}

	// data flow through return
	int m9() {
		int a = 0;
		return a;
	} 

	// data flow continue-target
	void m10() {
		int a = 0;
		int b = 0;
		while (b > 0) {
			if (b > 1) {
				continue;
			}
			int c;
		}
		a++;
	}

	// data flow throw in try-catch
	void m11() {
		int a = 0;
		try {
			int b;
			throw new Exception();
		} catch (Exception e) {
			int c;
		}
		a++;
	}

	// data flow through try-finally
	void m12() {
		int a = 0;
		try {
			int b;
		} finally {
			int c;
		}
		a++;
	}

	// data flow throw in try-catch
	void m13() {
		int a = 0;
		try {
			int b;
			throw new Exception();
		} catch (Exception e) {
			int c;
		} finally {
			int d;
		}
		a++;
	}

	// data flow through assert
	void m14() {
		int a = 0;
		int b = 0;
		assert b > 0;
		assert b > 0 : b;
		a++;
	}

	// data flow through enhanced for
	void m15() {
		int a = 0;
		Collection<String> coll = new LinkedList<String>();
		for (String str : coll) {
			if (str.equals("")) {
				break;
			}
		}
		a++;
	}

	// data flow from parameters via entry
	void m16(int a) {
		a++;
	}

	// data flow through catch clauses
	void m17() {
		int a = 0;
		try {
			throw new TestException();
		} catch (TestException e) {
			System.out.println(e.getMessage());
		}
		a++;
	}
	
}

class TestException extends Exception {
}

