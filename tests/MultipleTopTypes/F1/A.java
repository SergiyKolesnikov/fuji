//F1
class A {
    public static void main(String[] argv) {
        System.out.println(new A().m());
        System.out.println(new B().m());
        System.out.println(new C().m());
    }
    String m() {
        return "F1:A";
    }
}
class B {
    String m() {
        return "F1:B";
    }
}
class C {
    String m() {
        return "F1:C";
    }
}
