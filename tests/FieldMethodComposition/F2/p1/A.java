//F2.p1.A
package p1;
public class A{
    static String ss = "u";

    public static void methodOK(String s){
        original(s);
        System.out.println("F2: " + s.concat(ss));
    }

    public static void methodGapHelper() {
        System.out.println(methodGap());
    }

    public static String methodOriginal() {
        String line = original(); // nested original() call.
        return line + ": OK";
    }
}
