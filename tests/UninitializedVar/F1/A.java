public class A {
    public static void main(String[] argv) {
        new A().test(new Object());
    }

    public Object test(Object var) {
        Object o;
        o = test(o);
        return o;
    }
}
