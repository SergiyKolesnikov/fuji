package org.prevayler;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
import de.ovgu.cide.jakutil.*;
import java.lang.String;
import org.prevayler.implementation.replication.ServerListener;
import org.prevayler.foundation.network.OldNetwork;
import org.prevayler.implementation.replication.ClientPublisher;
import org.prevayler.foundation.network.OldNetworkImpl;
import java.io.IOException;
import java.lang.ClassNotFoundException;
public class PrevaylerFactory {
    @Stub
    public void original() {
        return ;
    }
    @Stub
    public void configurePrevalentSystem(java.lang.Object newPrevalentSystem) {
        return ;
    }
    @Stub
    public org.prevayler.Prevayler create() {
        return null;
    }
    @Stub
    public void configurePrevalenceDirectory(java.lang.String prevalenceDirectory) {
        return ;
    }
    public static class PrevaylerFactory_create {
        @Stub
        public org.prevayler.implementation.publishing.TransactionPublisher publisher;
        @Stub
        public void original() {
            return ;
        }
        @Stub
        public org.prevayler.PrevaylerFactory _this;
    }
}
