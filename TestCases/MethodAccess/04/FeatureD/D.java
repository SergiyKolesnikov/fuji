public class D {
    private static int l = 4711;
    public static int getValue() {
        return l;
    }
    private static void foo() {
        l = B.getValue();
    }
    private static void bar() {
        l = B.getValue();
    }
    private static void foobar() {
        l = B.getSecondValue();
    }
}