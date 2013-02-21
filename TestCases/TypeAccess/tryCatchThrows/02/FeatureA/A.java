public class A {
	public static void foo() {
		try {
			B.bar();
		} catch (E e) {
			// handle exception
		}
	}
}