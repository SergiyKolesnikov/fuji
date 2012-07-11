public class D {
    public static int l = 4711;
    private static void foo() {
        l = B.j;
    }
    private static void bar() {
        l = B.j;
    }
    private static void foobar() {
        l = B.jj;
    }
}