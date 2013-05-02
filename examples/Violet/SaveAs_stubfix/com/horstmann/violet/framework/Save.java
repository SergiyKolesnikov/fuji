package com.horstmann.violet.framework;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public interface Save {
    @Stub
    java.io.OutputStream getOutputStream();
    @Stub
    java.lang.String getName();
}
