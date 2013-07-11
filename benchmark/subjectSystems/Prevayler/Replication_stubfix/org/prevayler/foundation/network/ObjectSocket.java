package org.prevayler.foundation.network;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public interface ObjectSocket {
    @Stub
    java.lang.Object readObject();
    @Stub
    void writeObject(java.lang.Object object);
    @Stub
    void close();
}
