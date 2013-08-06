public class ParameterAccess {
	
	class Obj {
		private int i;
	}
	
	public void paramMethod(int x, int[] array, Obj o) {
		int a = x; // FanIn +1
		a = array[0]; // FanIn +1
		a = o.i; // FanIn +2: use of parameter o + use of field i of o
		
		array[0] = a; // FanOut +1
		o.i = a; // FanOut +2: change of parameter o + change of field i of o
		
		// using/changing parameters a second time should not change FanIn/Out
		a = x;
		a = array[0];
		a = o.i;
		array[0] = a;
		o.i = a;
	}
}