//F1
class A {
    static String s;
    static {
        s = "static string";
    }
    String si;
    {
        si = "instance string";
    }
    public static void main(String[] args) {
        System.out.println(A.s + " " + A.i);
        System.out.println(new A().si + " " + new A().ii);
    }
}
