public class B {	
	public static int j = 23;
    public static int jj = 5;
    public static int getValue() {
        return j;
    }
    public static int getSecondValue() {
        return jj;
    }
    private static void foo() {
        j = C.k;
        j = C.getValue();
    }
}
