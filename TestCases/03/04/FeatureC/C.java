public class C {
	public static int k = 47;
    public static int getValue() {
        return k;
    }
    private static void foo() {
        k = A.i;
        k = A.getValue();
    }
}