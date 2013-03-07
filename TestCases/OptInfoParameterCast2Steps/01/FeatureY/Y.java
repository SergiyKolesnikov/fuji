public class Y {
	public Y(C c) { }
	public void bar() {
		X x = new X(new A());
		x.foo(new A());
	}
}