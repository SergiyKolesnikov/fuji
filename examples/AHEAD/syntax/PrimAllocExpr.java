// Automatically generated code.  Edit at your own risk!
// Generated by bali2jak v2002.09.03.



public class PrimAllocExpr extends AllocationExpression {

    final public static int ARG_LENGTH = 2 ;
    final public static int TOK_LENGTH = 1 ;

    public ArrayDimsAndInits getArrayDimsAndInits () {
        
        return (ArrayDimsAndInits) arg [1] ;
    }

    public PrimitiveType getPrimitiveType () {
        
        return (PrimitiveType) arg [0] ;
    }

    public boolean[] printorder () {
        
        return new boolean[] {true, false, false} ;
    }

    public PrimAllocExpr setParms
    (AstToken tok0, PrimitiveType arg0, ArrayDimsAndInits arg1) {
        
        arg = new AstNode [ARG_LENGTH] ;
        tok = new AstTokenInterface [TOK_LENGTH] ;
        
        tok [0] = tok0 ;            /* "new" */
        arg [0] = arg0 ;            /* PrimitiveType */
        arg [1] = arg1 ;            /* ArrayDimsAndInits */
        
        InitChildren () ;
        return (PrimAllocExpr) this ;
    }

}
