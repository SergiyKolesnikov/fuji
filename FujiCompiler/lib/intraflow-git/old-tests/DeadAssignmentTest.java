import java.util.*;



public class DeadAssignmentTest {
	public static void main(String[] args) {

		// Not used -> Dead
		int p;

		// Not used (with init) -> Dead
		int q = 0;		

		// Not used, May have side effects
		int b = a();

		// Used
		int c = 0;

		// Used, May have side effects, 
		int d = 0 + a();

		// Used, Dead uses -> Dead
		int e = 0;


		// Not used, May have side effects
		c = a() + d;

		// Not used -> Dead
		e = 4 + 2; 

		I i = new I() {
			public void m() {
			}
		};

		i.m();
	}

	private static int a() {
		return 0;
	}
}

interface I {
	void m();
}
