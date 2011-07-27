//F2
package some;
import java.util.Vector;  // This type reference is ignored because the type is
                          // used later any way.  And if not, than the type
                          // reference is irrelevant.
class A extends Vector implements I {
    void refinedInF2() {
        original();                        // 1. original method ref
        Object o = (Object) new Boolean(); // 7. type ref: constructor call
                                           // 8. type ref: cast
    }

    void methodInF2() {
        new A().methodInF1(new Long(), 0); // 1. method ref
        new A().methodI().methodI().methodI(); // 2. method refs
        String[] stringArrayLocal = new A().stringArray; // 1. field ref
        //asdf // TODO if uncoment asdf, the refinment won't compile
    }
}
