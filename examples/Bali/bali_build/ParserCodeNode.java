// Automatically generated code.  Edit at your own risk!
// Generated by bali2jak v2002.09.03.



public class ParserCodeNode extends ParserCode {

    final public static int ARG_LENGTH = 1 ;
    final public static int TOK_LENGTH = 2 ;

    public Block getBlock () {
        
        return (Block) arg [0] ;
    }

    public AstToken get_CODE () {
        
        return (AstToken) tok [1] ;
    }

    public boolean[] printorder () {
        
        return new boolean[] {true, false, true} ;
    }

    public ParserCodeNode setParms (AstToken tok0, Block arg0, AstToken tok1) {
        
        arg = new AstNode [ARG_LENGTH] ;
        tok = new AstTokenInterface [TOK_LENGTH] ;
        
        tok [0] = tok0 ;            /* _CODE */
        arg [0] = arg0 ;            /* Block */
        tok [1] = tok1 ;            /* _CODE */
        
        InitChildren () ;
        return (ParserCodeNode) this ;
    }

}
