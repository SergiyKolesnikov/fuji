// Automatically generated code.  Edit at your own risk!
// Generated by bali2jak v2002.09.03.



public class CatchStmt extends Catch {

    final public static int ARG_LENGTH = 2 ;
    final public static int TOK_LENGTH = 3 ;

    public Block getBlock () {
        
        return (Block) arg [1] ;
    }

    public FormalParameter getFormalParameter () {
        
        return (FormalParameter) arg [0] ;
    }

    public boolean[] printorder () {
        
        return new boolean[] {true, true, false, true, false} ;
    }

    public CatchStmt setParms
    (AstToken tok0, AstToken tok1, FormalParameter arg0, AstToken tok2, Block arg1)
    {
        
        arg = new AstNode [ARG_LENGTH] ;
        tok = new AstTokenInterface [TOK_LENGTH] ;
        
        tok [0] = tok0 ;            /* "catch" */
        tok [1] = tok1 ;            /* "(" */
        arg [0] = arg0 ;            /* FormalParameter */
        tok [2] = tok2 ;            /* ")" */
        arg [1] = arg1 ;            /* Block */
        
        InitChildren () ;
        return (CatchStmt) this ;
    }

}
