package com.horstmann.violet.framework;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public interface Edge {
    @Stub
    com.horstmann.violet.framework.Node getEnd();
    @Stub
    com.horstmann.violet.framework.Node getStart();
    @Stub
    void connect(com.horstmann.violet.framework.Node aStart, com.horstmann.violet.framework.Node anEnd);
}
