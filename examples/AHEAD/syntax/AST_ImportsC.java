// Automatically generated code.  Edit at your own risk!
// Generated by bali2jak v2002.09.03.



public class AST_ImportsC extends JakartaSST {

    final public static int ARG_LENGTH = 1 ;
    final public static int TOK_LENGTH = 2 ;

    public AST_Imports getAST_Imports () {
        
        AstNode node = arg[0].arg [0] ;
        return (node != null) ? (AST_Imports) node : null ;
    }

    public AstToken getIMP_BEGIN () {
        
        return (AstToken) tok [0] ;
    }

    public AstToken getIMP_END () {
        
        return (AstToken) tok [1] ;
    }

    public boolean[] printorder () {
        
        return new boolean[] {true, false, true} ;
    }

    public AST_ImportsC setParms (AstToken tok0, AstOptNode arg0, AstToken tok1) {
        
        arg = new AstNode [ARG_LENGTH] ;
        tok = new AstTokenInterface [TOK_LENGTH] ;
        
        tok [0] = tok0 ;            /* IMP_BEGIN */
        arg [0] = arg0 ;            /* [ AST_Imports ] */
        tok [1] = tok1 ;            /* IMP_END */
        
        InitChildren () ;
        return (AST_ImportsC) this ;
    }

}
