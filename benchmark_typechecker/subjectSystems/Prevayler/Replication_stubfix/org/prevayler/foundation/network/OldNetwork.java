package org.prevayler.foundation.network;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public interface OldNetwork {
    @Stub
    org.prevayler.foundation.network.ObjectSocket openSocket(java.lang.String serverIpAddress, int serverPort);
    @Stub
    org.prevayler.foundation.network.ObjectServerSocket openObjectServerSocket(int port);
}
