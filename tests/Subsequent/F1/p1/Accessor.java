// F1.p1.Accessor
package p1;
public class Accessor {
    public static void main(String[] argv) {
        new Accessor().F1_p1_Accessor();
        new Later().F2_p1_Later();
        new Later().F3_p1_Later();
        new lib.External().lib_External();
    }

    public void F1_p1_Accessor() {
        System.out.println("OK F1_p1_Accessor()");
    }
}
