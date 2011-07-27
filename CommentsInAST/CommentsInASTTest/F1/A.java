/* Comments as AST nodes on this level are not implemented.  Synactic error. */
class A {
    /* Comments as AST nodes on this level are implemented. */
    void foo() {
        /*
         * Comments as AST nodes on this level are not implemented.
         * Synactic error.
         */
    }
}
