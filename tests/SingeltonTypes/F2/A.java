//F2.A
refines class A {

    static int integer() {
        return 1;
    }

    static String[] string() {
        String[] s = {"right"};
        return s;
    }

    public static void main(String[] argv) {
        System.out.println(integer());
        System.out.println(string()[0]);
    }
}
