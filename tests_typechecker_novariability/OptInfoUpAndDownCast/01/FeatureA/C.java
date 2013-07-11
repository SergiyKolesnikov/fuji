public class C {
	public void foo() {
		B b = (B) new A();
		A a = (A) new B();
	}
}