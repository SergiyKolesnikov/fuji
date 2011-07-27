public enum A {
    ENUM1("foo", 42);
    private String string;
    private int integer;
    private A(String s, int i) {
        this.string = s;
        this.integer = i;
    }
}
