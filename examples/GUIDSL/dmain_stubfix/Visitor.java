import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public interface Visitor {
    @Stub
    void action(Pat n);
    @Stub
    void action(GTerm n);
    @Stub
    void action(Optid n);
    @Stub
    void action(Model n);
    @Stub
    void action(NExpr n);
    @Stub
    void action(TermName n);
    @Stub
    void action(BImplies n);
    @Stub
    void action(Var n);
    @Stub
    void action(SimplePattern n);
    @Stub
    void action(StarTerm n);
    @Stub
    void action(BNot n);
    @Stub
    void action(EExpr n);
    @Stub
    void action(PlusTerm n);
    @Stub
    void action(Cons n);
    @Stub
    void action(AstOptNode n);
    @Stub
    void action(Vars n);
    @Stub
    void action(AstList n);
    @Stub
    void action(Strlit n);
    @Stub
    void action(Prods n);
    @Stub
    void action(OptTerm n);
    @Stub
    void action(ESList n);
    @Stub
    void action(BIff n);
    @Stub
    void action(OExpr n);
    @Stub
    void action(MainModel n);
    @Stub
    void action(BExpr n);
    @Stub
    void action(GProd n);
    @Stub
    void action(AvarList n);
    @Stub
    void action(BAnd n);
    @Stub
    void action(BOr n);
    @Stub
    void action(ExprList n);
    @Stub
    void action(Expr n);
    @Stub
    void action(AstListNode n);
    @Stub
    void action(BChoose1 n);
    @Stub
    void action(Paren n);
    @Stub
    void action(EStmt n);
    @Stub
    void action(ExprStmt n);
    @Stub
    void action(AExpr n);
    @Stub
    void action(AstNode n);
    @Stub
    void action(Bvar n);
    @Stub
    void action(GPattern n);
    @Stub
    void action(TermList n);
    @Stub
    void action(Opt n);
    @Stub
    void action(VarStmt n);
    @Stub
    void action(IExpr n);
    @Stub
    void action(Avar n);
    @Stub
    void action(ConsStmt n);
    @Stub
    void action(Pats n);
    @Stub
    void action(GProduction n);
    @Stub
    void action(Opts n);
}
