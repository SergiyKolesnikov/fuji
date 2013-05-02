package org.prevayler.implementation;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
import java.lang.Error;
import org.prevayler.implementation.snapshot.GenericSnapshotManager;
import java.io.IOException;
public class PrevalentSystemGuard implements org.prevayler.implementation.publishing.TransactionSubscriber {
    @Stub
    public long _systemVersion;
    @Stub
    public void subscribeTo(org.prevayler.implementation.publishing.TransactionPublisher publisher) {
        return ;
    }
    @Stub
    public java.lang.Object executeQuery(org.prevayler.Query sensitiveQuery, org.prevayler.Clock clock) {
        return null;
    }
    @Stub
    public java.lang.Object prevalentSystem() {
        return null;
    }
    @Stub
    public java.lang.Object _prevalentSystem;
    @Stub
    public PrevalentSystemGuard(java.lang.Object prevalentSystem, long systemVersion, org.prevayler.foundation.serialization.Serializer journalSerializer) {
    }
}
