//F2
refines class A {

    public A(int i) {
        y = 2 * i;
    }

    public static void main(String[] args) {
        A a = new A(21);
        System.out.println("shuold be 21 42: " + a.x + " " + a.y);
    }
}
