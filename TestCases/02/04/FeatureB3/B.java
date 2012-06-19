public class B {	
	private static int j = 23;
    private static int jj = 5;
    public static int getValue() {
        return j;
    }
    public static int getSecondValue() {
        return jj;
    }
    private static void foo() {
        j = C.getValue();
    }
}
