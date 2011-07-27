package p1;
public class C {
    void foo() {
        String s;
        s = (new p1.A()).privateFeatureA2;      // type error (access violation)
        s = (new p1.A()).privateSubsequentA2;   // type error (access violation)
        s = (new p1.A()).privateProgramA2;      // type error (access violation)

        s = (new p1.A()).protectedFeatureA2;    // type error (access violation)
        s = (new p1.A()).protectedSubsequentA2; // ok
        s = (new p1.A()).protectedProgramA2;    // ok

        s = (new p1.A()).packageFeatureA2;      // type error (access violation)
        s = (new p1.A()).packageSubsequentA2;   // ok
        s = (new p1.A()).packageProgramA2;      // ok

        s = (new p1.A()).publicFeatureA2;       // type error (access violation)
        s = (new p1.A()).publicSubsequentA2;    // ok
        s = (new p1.A()).publicProgramA2;       // ok
    }
}
