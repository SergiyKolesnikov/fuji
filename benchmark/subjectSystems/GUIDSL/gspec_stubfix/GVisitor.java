import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public interface GVisitor {
    @Stub
    void action(prim n);
    @Stub
    void action(production n);
    @Stub
    void action(grammar n);
    @Stub
    void action(variable n);
    @Stub
    void action(plus n);
    @Stub
    void action(term n);
    @Stub
    void action(optprod n);
    @Stub
    void action(optprim n);
    @Stub
    void action(pattern n);
    @Stub
    void action(prod n);
    @Stub
    void action(star n);
}
