package org.prevayler.foundation.network;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public interface NetworkReceiverFactory {
    @Stub
    org.prevayler.foundation.network.ObjectReceiver newReceiver(org.prevayler.foundation.network.Service service, org.prevayler.foundation.network.ObjectSocket socket);
}
