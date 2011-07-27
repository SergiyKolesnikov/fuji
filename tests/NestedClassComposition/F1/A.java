//F1
public class A {
    public static void main(String[] argv) {
        new Nested().m();
    }
    public static class Nested {
        int i = 0;
        public void m() {
            System.out.println(i);
        }
    }
}
