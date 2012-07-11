public class A {
    private static int i = 42;
    public static int getValue() {
        return i;
    }
    private static void foo() {
        i = B.getValue();
    }
}
