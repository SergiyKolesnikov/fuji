// Automatically generated code.  Edit at your own risk!
// Generated by bali2jak v2002.09.03.



public class BIff extends EExpr {

    final public static int ARG_LENGTH = 2 ;
    final public static int TOK_LENGTH = 1 ;

    public EExpr getEExpr () {
        
        return (EExpr) arg [1] ;
    }

    public IExpr getIExpr () {
        
        return (IExpr) arg [0] ;
    }

    public boolean[] printorder () {
        
        return new boolean[] {false, true, false} ;
    }

    public BIff setParms (IExpr arg0, AstToken tok0, EExpr arg1) {
        
        arg = new AstNode [ARG_LENGTH] ;
        tok = new AstTokenInterface [TOK_LENGTH] ;
        
        arg [0] = arg0 ;            /* IExpr */
        tok [0] = tok0 ;            /* "iff" */
        arg [1] = arg1 ;            /* EExpr */
        
        InitChildren () ;
        return (BIff) this ;
    }

}
