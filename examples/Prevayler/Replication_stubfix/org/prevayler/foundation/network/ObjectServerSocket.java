package org.prevayler.foundation.network;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public interface ObjectServerSocket {
    @Stub
    void close();
    @Stub
    org.prevayler.foundation.network.ObjectSocket accept();
}
