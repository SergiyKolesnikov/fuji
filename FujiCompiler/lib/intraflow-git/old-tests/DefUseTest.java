package test;

public class DefUseTest {

	void m1() {
		int a; // dead
		a = 0; // dead
	}

	void m2(int a) {
		a = 0; // dead
	}

	void m3(int a) {
		a++; // dead
	}

	void m4(int a, int b) {
		a = 2 + 3 + b; // dead - isDead
		b = 4; // dead - all uses of b's decl are dead
		a = 3; // dead - all uses of a's decl are dead
		int c = a + b; // dead
	}

	void m5(int b) {
		int a = 0;
		b = 0;
		int c = a + b;
	}

	void m6() {
		int a = 0;
		if (a > 0) {
			a++;
		} else {
			a += 3;
		}
		a++; // dead
	}

}
