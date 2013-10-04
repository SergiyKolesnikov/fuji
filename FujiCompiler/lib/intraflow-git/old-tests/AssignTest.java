package test;

public class AssignTest {

   int m() {
       	int a = 0;
		while (a < 10) {
			a += 2;
		}
		return a;
   }

	int attr = 0;


	void m1() {

		int a;
		int b;
		a = b = 0 + attr++;

		int c = a++;
	}

	void m2() {
		int a;
		int b = 0;
		a = 0;
		b = a;
		b++;
		b *= 0;
	}


	String m3(int a, boolean b) {

	    String idTok;
	    idTok = null;

	    switch (a) {
	    case 0: {
	        if(b) {
	          idTok = "0";
    	 	}
	        break ;
    	}
    	case 1: {
        	if(!b) {
          		idTok = "1";
        	}
        	break ;
      	}
      	default: {
	      throw new RuntimeException();
      	}
    	}
    	return idTok;
  	}

	int m4() {

		int a = 0;
	    while(a < 10){
	        a *= 2;
    	}
		return a;
	}


	void m5() {
//		int a = 0;
		boolean changed = true;
		while(changed) {
			changed = false;
/*
			if(a > 5) {
       			continue ;
       		}
			if (a == 0) {
				changed = false;
        	}
			if (a == 4) {
				changed = false;
			}
*/
      	}
	}
/*
  public int getChildCount(Object parent) {
    int i = 0;
    while(parent != null){
      i++;
    }
    return i;
  }

  public int getChildCount2(Object parent) {
    int i = 0;
    while(parent != null && i > 10){
      i++;
    }
    return i;
  }
*/

}
