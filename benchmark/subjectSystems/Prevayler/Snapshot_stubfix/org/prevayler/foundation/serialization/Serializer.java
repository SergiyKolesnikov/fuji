package org.prevayler.foundation.serialization;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public interface Serializer {
    @Stub
    java.lang.Object readObject(java.io.InputStream stream);
    @Stub
    void writeObject(java.io.OutputStream stream, java.lang.Object object);
}
