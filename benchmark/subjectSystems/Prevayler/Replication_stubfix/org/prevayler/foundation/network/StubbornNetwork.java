package org.prevayler.foundation.network;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public interface StubbornNetwork {
    @Stub
    org.prevayler.foundation.network.ObjectSocket newInstance(java.lang.String ipAddress, int port);
}
