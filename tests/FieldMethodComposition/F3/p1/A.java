//F3.p1.A
package p1;
public class A{
    static String ss = "K";

    public static void methodOK(String s){
        if (true) {
            if (true) {
                original(s); // nested original() call.
            }
        }
        System.out.println("F3: " + s.concat(ss));
    }

    public static String methodGap() {
        return "methodGap: OK";
    }
}
