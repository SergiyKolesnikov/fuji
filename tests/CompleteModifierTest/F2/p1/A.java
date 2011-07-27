package p1;
public class A {
    private feature String privateFeatureA2 = "privateFeatureA2";
    private subsequent String privateSubsequentA2 = "privateSubsequentA2";
    private program String privateProgramA2 = "privateProgramA2";

    protected feature String protectedFeatureA2 = "protectedFeatureA2";
    protected subsequent String protectedSubsequentA2 = "protectedSubsequentA2";
    protected program String protectedProgramA2 = "protectedProgramA2";

    feature String packageFeatureA2 = "packageFeatureA2";
    subsequent String packageSubsequentA2 = "packageSubsequentA2";
    program String packageProgramA2 = "packageProgramA2";

    public feature String publicFeatureA2 = "publicFeatureA2";
    public subsequent String publicSubsequentA2 = "publicSubsequentA2";
    public program String publicProgramA2 = "publicProgramA2";

    void foo() {
        String s;
        s = this.privateFeatureA2;      // ok
        s = this.privateSubsequentA2;   // ok
        s = this.privateProgramA2;      // ok

        s = this.protectedFeatureA2;    // ok
        s = this.protectedSubsequentA2; // ok
        s = this.protectedProgramA2;    // ok

        s = this.packageFeatureA2;      // ok
        s = this.packageSubsequentA2;   // ok
        s = this.packageProgramA2;      // ok

        s = this.publicFeatureA2;       // ok
        s = this.publicSubsequentA2;    // ok
        s = this.publicProgramA2;       // ok
    }
}
