public class D {
    public static int l = 4711;
    public static int getValue() {
        return l;
    }
    private static void foo() {
        l = B.j;
        l = B.getValue();
    }
    private static void bar() {
        l = B.j;
        l = B.getValue();
    }
    private static void foobar() {
        l = B.jj;
        l = B.getSecondValue();
    }
}