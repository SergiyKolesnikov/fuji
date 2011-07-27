public class A {
    public static void main(String[] args) {
        new A().hook(new B());
    }
    private static class B {
        public int i = 1;
    }
    protected void hook(B b) {
    }
}
