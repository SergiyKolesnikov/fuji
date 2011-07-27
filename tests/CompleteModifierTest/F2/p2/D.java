package p2;
public class D extends p1.A {
    void foo() {
        String s;
        s = this.privateFeatureA2;      // type error (access violation)
        s = this.privateSubsequentA2;   // type error (access violation)
        s = this.privateProgramA2;      // type error (access violation)

        s = this.protectedFeatureA2;    // ok
        s = this.protectedSubsequentA2; // ok
        s = this.protectedProgramA2;    // ok

        s = this.packageFeatureA2;      // type error (access violation)
        s = this.packageSubsequentA2;   // type error (access violation)
        s = this.packageProgramA2;      // type error (access violation)

        s = this.publicFeatureA2;       // ok
        s = this.publicSubsequentA2;    // ok
        s = this.publicProgramA2;       // ok
    }
}
