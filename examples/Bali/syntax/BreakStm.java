// Automatically generated code.  Edit at your own risk!
// Generated by bali2jak v2002.09.03.



public class BreakStm extends BreakStatement {

    final public static int ARG_LENGTH = 1 ;
    final public static int TOK_LENGTH = 2 ;

    public QName getQName () {
        
        AstNode node = arg[0].arg [0] ;
        return (node != null) ? (QName) node : null ;
    }

    public boolean[] printorder () {
        
        return new boolean[] {true, false, true} ;
    }

    public BreakStm setParms (AstToken tok0, AstOptNode arg0, AstToken tok1) {
        
        arg = new AstNode [ARG_LENGTH] ;
        tok = new AstTokenInterface [TOK_LENGTH] ;
        
        tok [0] = tok0 ;            /* "break" */
        arg [0] = arg0 ;            /* [ QName ] */
        tok [1] = tok1 ;            /* ";" */
        
        InitChildren () ;
        return (BreakStm) this ;
    }

}
