public class Caller {
	public Caller() throws Exception { 
		TestClass obj = (TestClass)Class.forName("TestClass").newInstance();
	}
}
