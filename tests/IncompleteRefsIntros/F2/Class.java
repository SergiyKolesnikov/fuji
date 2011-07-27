// F2
class Class extends java.util.ArrayList implements I2 {
    Boolean f = true;
    void m(int p) { String f2 = 0; }
    void m2(String p) {}
    static int[] staticArrayF2;
    static int i;
    static {
        staticArrayF2 = staticArrayF1;
        i = staticArrayF1.length;
    }
    {
        staticArrayF2 = staticArrayF1;
        i = staticArrayF1.length;
    }
}
