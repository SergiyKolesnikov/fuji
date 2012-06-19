public class A {
    public static int i = 42;
    public static int getValue() {
        return i;
    }
    private static void foo() {
        i = B.j;
        i = B.getValue();
    }
}
