package p1;
public class A {
    void foo() {
        String s;
        s = this.privateFeatureA2;      // type error (access violation)
        s = this.privateSubsequentA2;   // type error (access violation)
        s = this.privateProgramA2;      // ok

        s = this.protectedFeatureA2;    // type error (access violation)
        s = this.protectedSubsequentA2; // type error (access violation)
        s = this.protectedProgramA2;    // ok

        s = this.packageFeatureA2;      // type error (access violation)
        s = this.packageSubsequentA2;   // type error (access violation)
        s = this.packageProgramA2;      // ok

        s = this.publicFeatureA2;       // type error (access violation)
        s = this.publicSubsequentA2;    // type error (access violation)
        s = this.publicProgramA2;       // ok
    }
}
