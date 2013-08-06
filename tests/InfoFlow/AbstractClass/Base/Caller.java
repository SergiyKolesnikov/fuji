public class Caller {
	public void call() {
		AbstractClass obj = new Implementation(); //FanIn & FanOut +1 for both features
		obj.method1(); //Just FanOut +1 for feature 0, FanIn +1 for feature 1
		obj.method2(); //FanIn & FanOut +1 for both features
	}
}