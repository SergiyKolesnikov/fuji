package tmp;

import basic.B;

public class C {
	public static B b = new A();
	public void test() {
		B a = new A();
		a.bar();
		b.bar();
		new A().bar();
		(new A()).bar();
		((B) new A()).bar();
		A.foobar();
		// System.out.println(A.i);
	}
}