// Automatically generated code.  Edit at your own risk!
// Generated by bali2jak v2002.09.03.



public class RTPre extends PrimaryPrefix {

    final public static int ARG_LENGTH = 1 ;
    final public static int TOK_LENGTH = 2 ;

    public AST_TypeName getAST_TypeName () {
        
        return (AST_TypeName) arg [0] ;
    }

    public boolean[] printorder () {
        
        return new boolean[] {false, true, true} ;
    }

    public RTPre setParms (AST_TypeName arg0, AstToken tok0, AstToken tok1) {
        
        arg = new AstNode [ARG_LENGTH] ;
        tok = new AstTokenInterface [TOK_LENGTH] ;
        
        arg [0] = arg0 ;            /* AST_TypeName */
        tok [0] = tok0 ;            /* "." */
        tok [1] = tok1 ;            /* "class" */
        
        InitChildren () ;
        return (RTPre) this ;
    }

}
