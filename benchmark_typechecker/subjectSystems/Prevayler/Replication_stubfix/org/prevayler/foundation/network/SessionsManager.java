package org.prevayler.foundation.network;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public interface SessionsManager {
    @Stub
    org.prevayler.foundation.network.StubbornNetworkProxy find(org.prevayler.foundation.network.NetworkSessionId sessionId);
    @Stub
    void remove(org.prevayler.foundation.network.NetworkSessionId sessionId);
    @Stub
    org.prevayler.foundation.network.NetworkSessionId add(org.prevayler.foundation.network.StubbornNetworkProxy receiver);
}
