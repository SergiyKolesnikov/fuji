public class FieldAccessInConstructor {
	
	class Obj {
		private int i;
	}
	
	int x;
	Obj o;
	
	FieldAccessInConstructor() {
		int a = x; // FanIn +1
		a = o.i; // FanIn +2: use of field o + use of field i of o  
		
		x = a; // FanOut +1
		o.i = a; // FanOut +2: change of field o + change of field i of o
			
		// using/changing fields a second time should not change FanIn/Out
		a = x;
		a = o.i;
		x = a;
		o.i = a;
	}
}