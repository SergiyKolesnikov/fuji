package org.prevayler.implementation;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
import java.lang.Object;
import org.prevayler.implementation.PrevalentSystemGuard;
import java.lang.Exception;
import org.prevayler.foundation.serialization.Serializer;
import org.prevayler.Query;
import org.prevayler.implementation.publishing.TransactionPublisher;
import org.prevayler.implementation.snapshot.GenericSnapshotManager;
import java.io.IOException;
import java.lang.ClassNotFoundException;
public class PrevaylerImpl implements org.prevayler.Prevayler {
    @Stub
    public PrevaylerImpl(org.prevayler.implementation.publishing.TransactionPublisher transactionPublisher, org.prevayler.foundation.serialization.Serializer journalSerializer) {
    }
    @Stub
    public org.prevayler.Clock clock() {
        return null;
    }
    @Stub
    public org.prevayler.implementation.publishing.TransactionPublisher _publisher;
}
