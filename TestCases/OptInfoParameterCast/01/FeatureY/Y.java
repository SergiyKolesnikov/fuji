public class Y {
	public Y(B b) { }
	public void bar() {
		X x = new X(new A());
		x.foo(new A());
	}
}