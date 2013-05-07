public class Z {
	private A a = new A();
	private C c = a;
	public void foo() {
		A a2 = new A();
		C c = a2;
		C c2 = null;
		c2 = a2;
	}
}