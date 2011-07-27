//F1
public class A {
    String string = "F1";
    void method() {
        /* original() call in a base class.  It is legitimate as long this is
           processed by fuji with -forRefs key.  Refs and introduces generation
           must not depend on the composition order. */
        original();
    }
}
