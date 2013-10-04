package test;

public class ExprTest {

/*
	void v(int a, int b, int c, String s){
		a = (b = 5 + 1) * (-(c + 2));
		b += 7 % (a = 8);
		a = b = 9;
		
		c = ++a / b++;
		
		s = (String)(s + "str");
		
		if((s = "str") instanceof String) ;
		
		a = a > 0 ? b + 2 : c - 3;
	
	}
*/

    void m1(Class clazz) {
        int pos = 4;
        Object obj = null;
        while(pos >= 0 && !(clazz.isInstance(obj = null))){
          pos--;
        }
        if(pos >= 0) {
          System.out.println(obj);
        }
    }

}
