package org.prevayler.foundation.network;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public interface ObjectReceiver {
    @Stub
    void close();
    @Stub
    void receive(java.lang.Object object);
}
