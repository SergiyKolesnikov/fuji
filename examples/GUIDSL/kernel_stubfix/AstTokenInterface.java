import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public interface AstTokenInterface {
    @Stub
    void printWhitespaceOnly(AstProperties props);
    @Stub
    java.lang.String tokenName();
    @Stub
    void print();
    @Stub
    void print(AstProperties props);
    @Stub
    void setTokenName(java.lang.String replacement);
    @Stub
    java.lang.Object clone();
    @Stub
    java.lang.String getTokenName();
    @Stub
    void reduce2java(AstProperties props);
    @Stub
    boolean Equ(AstTokenInterface x);
}
