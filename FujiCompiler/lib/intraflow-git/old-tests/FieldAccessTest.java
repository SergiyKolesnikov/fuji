package test;

public class FieldAccessTest {

	int a = 0;	

	void m1() {
		this.a = 0; // should not be dead
	}
}
