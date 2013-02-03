public class Y {
	public Y(B b) {
		
	}
	public void bar() {
		X x = new X();
		x.foo(new A());
		X x2 = new X(new A());
	}
}