//F2
public class A {
    String string = "F2";
    void method() {

        // access to duplicated field string.
        string = "F2-method";

        // access to a field of a duplicated field string.
        String s = string.CASE_INSENSITIVE_ORDER;

        string.isEmpty();

        // nested original.
        if (true) {
            original();
        }
    }
}
