public class A {
    protected void hook(B b) {
        System.out.println(b.i);
        original(b);
    }
}
