// Automatically generated code.  Edit at your own risk!
// Generated by bali2jak v2002.09.03.



public class AST_TypeNameListC extends JakartaSST {

    final public static int ARG_LENGTH = 1 ;
    final public static int TOK_LENGTH = 2 ;

    public AST_TypeNameList getAST_TypeNameList () {
        
        return (AST_TypeNameList) arg [0] ;
    }

    public AstToken getTLST_BEGIN () {
        
        return (AstToken) tok [0] ;
    }

    public AstToken getTLST_END () {
        
        return (AstToken) tok [1] ;
    }

    public boolean[] printorder () {
        
        return new boolean[] {true, false, true} ;
    }

    public AST_TypeNameListC setParms
    (AstToken tok0, AST_TypeNameList arg0, AstToken tok1) {
        
        arg = new AstNode [ARG_LENGTH] ;
        tok = new AstTokenInterface [TOK_LENGTH] ;
        
        tok [0] = tok0 ;            /* TLST_BEGIN */
        arg [0] = arg0 ;            /* AST_TypeNameList */
        tok [1] = tok1 ;            /* TLST_END */
        
        InitChildren () ;
        return (AST_TypeNameListC) this ;
    }

}
