package org.prevayler.foundation.network;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public interface Network {
    @Stub
    void startService(org.prevayler.foundation.network.Service service, int port);
    @Stub
    void stopService(int port);
    @Stub
    org.prevayler.foundation.network.ObjectReceiver findServer(java.lang.String ipAddress, int port, org.prevayler.foundation.network.ObjectReceiver client);
}
