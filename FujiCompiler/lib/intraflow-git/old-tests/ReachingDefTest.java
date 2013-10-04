package test;

import java.lang.reflect.*;

class ReachingDefTest {

	void m1() {
		int a = 0; // Dead
		int b = a; // Dead
	}

	void m2() {
		String className = "dfsdf";
		Class c = null;
		try {
			c = Class.forName(className);
			Class[] tokenArgType = new Class[]{ Object.class } ;
			Constructor ctor = c.getConstructor(tokenArgType);
			if(ctor != null) {
				ctor.newInstance(new Object[]{ } );
      			}
    		}
		catch (Exception e) {
			throw new IllegalArgumentException("Invalid class or can\'t make instance, " + className);
    		}
	}

	void m3() {

		String app = "hkjhkj";
		Class c = null;
    		Method m = null;
		Object appObj = null;
		try {
			c = Class.forName(app);
			appObj = c.newInstance();
    		}
		catch (Exception e) {
			try {
        			if(!app.startsWith("antlr.build.")) {
					c = Class.forName("antlr.build." + app);
        			}
				System.err.println("no such application " + app + e);
			}
			catch (Exception e2) {
				System.err.println("no such application " + app + e2);
      			}
    		}
		if(c == null || appObj == null) {
			return ;
    		}
		try {
			m = c.getMethod("fsdf", new Class[]{ Object.class } );
      			m.invoke(appObj, new Object[]{ this } );
    		}
		catch (Exception e) {
			System.err.println("no such action for application " + app + e);
    		}
	}
}
