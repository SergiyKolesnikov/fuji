// Automatically generated code.  Edit at your own risk!
// Generated by bali2jak v2002.09.03.



public class PlusUE extends UnaryExpression {

    final public static int ARG_LENGTH = 1 ;
    final public static int TOK_LENGTH = 1 ;

    public UnaryExpression getUnaryExpression () {
        
        return (UnaryExpression) arg [0] ;
    }

    public boolean[] printorder () {
        
        return new boolean[] {true, false} ;
    }

    public PlusUE setParms (AstToken tok0, UnaryExpression arg0) {
        
        arg = new AstNode [ARG_LENGTH] ;
        tok = new AstTokenInterface [TOK_LENGTH] ;
        
        tok [0] = tok0 ;            /* "+" */
        arg [0] = arg0 ;            /* UnaryExpression */
        
        InitChildren () ;
        return (PlusUE) this ;
    }

}
