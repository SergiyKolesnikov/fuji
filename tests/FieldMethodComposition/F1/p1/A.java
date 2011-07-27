//F1.p1.A/
package p1;
public class A{
    public static void main(String[] argv) {
        methodOK("O");
        System.out.println(methodGap());
        System.out.println(methodOriginal());
    }

    static String ss = "ss: bad";

    public static void methodOK(String s){
        System.out.println("F1: " + s.concat(ss));
    }

    public static String methodGap() {
        return "methodGap: bad";
    }

    public static String methodOriginal() {
        return "methodOriginal";
    }
}
