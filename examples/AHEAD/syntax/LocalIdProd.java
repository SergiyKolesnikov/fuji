// Automatically generated code.  Edit at your own risk!
// Generated by bali2jak v2002.09.03.



public class LocalIdProd extends TypeDeclaration {

    final public static int ARG_LENGTH = 1 ;
    final public static int TOK_LENGTH = 2 ;

    public QNameList getQNameList () {
        
        return (QNameList) arg [0] ;
    }

    public boolean[] printorder () {
        
        return new boolean[] {true, false, true} ;
    }

    public LocalIdProd setParms (AstToken tok0, QNameList arg0, AstToken tok1) {
        
        arg = new AstNode [ARG_LENGTH] ;
        tok = new AstTokenInterface [TOK_LENGTH] ;
        
        tok [0] = tok0 ;            /* "Local_Id" */
        arg [0] = arg0 ;            /* QNameList */
        tok [1] = tok1 ;            /* ";" */
        
        InitChildren () ;
        return (LocalIdProd) this ;
    }

}
