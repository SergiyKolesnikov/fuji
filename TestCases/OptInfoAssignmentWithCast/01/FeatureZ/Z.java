public class Z {
	private A a = new A();
	private B b = a;
	public void foo() {
		A a2 = new A();
		B b = a2;
		B b2 = null;
		b2 = a2;
	}
}