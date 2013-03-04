public class D {
	public static C c = new A();
	public void test() {
		C a = new A();
		a.bar();
		c.bar();
		new A().bar();
		(new A()).bar();
		((C) new A()).bar();
		A.foobar();
	}
}