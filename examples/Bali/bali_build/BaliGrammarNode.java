// Automatically generated code.  Edit at your own risk!
// Generated by bali2jak v2002.09.03.



public class BaliGrammarNode extends BaliGrammarRule {

    final public static int ARG_LENGTH = 1 ;
    final public static int TOK_LENGTH = 3 ;

    public AstToken getIDENTIFIER () {
        
        return (AstToken) tok [0] ;
    }

    public Productions getProductions () {
        
        return (Productions) arg [0] ;
    }

    public boolean[] printorder () {
        
        return new boolean[] {true, true, false, true} ;
    }

    public BaliGrammarNode setParms
    (AstToken tok0, AstToken tok1, Productions arg0, AstToken tok2) {
        
        arg = new AstNode [ARG_LENGTH] ;
        tok = new AstTokenInterface [TOK_LENGTH] ;
        
        tok [0] = tok0 ;            /* IDENTIFIER */
        tok [1] = tok1 ;            /* ":" */
        arg [0] = arg0 ;            /* Productions */
        tok [2] = tok2 ;            /* ";" */
        
        InitChildren () ;
        return (BaliGrammarNode) this ;
    }

}
