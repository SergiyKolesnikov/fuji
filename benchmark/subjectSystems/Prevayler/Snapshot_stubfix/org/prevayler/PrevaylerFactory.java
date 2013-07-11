package org.prevayler;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
import de.ovgu.cide.jakutil.*;
import org.prevayler.implementation.PrevaylerDirectory;
import java.lang.String;
import org.prevayler.implementation.snapshot.NullSnapshotManager;
import org.prevayler.implementation.replication.ClientPublisher;
import org.prevayler.foundation.serialization.SkaringaSerializer;
import org.prevayler.foundation.serialization.JavaSerializer;
import org.prevayler.foundation.serialization.XStreamSerializer;
import java.io.Serializable;
import org.prevayler.implementation.snapshot.GenericSnapshotManager;
import java.lang.ClassNotFoundException;
import java.util.Collections;
import org.prevayler.implementation.journal.PersistentJournal;
import org.prevayler.implementation.publishing.CentralPublisher;
import org.prevayler.implementation.PrevaylerImpl;
import org.prevayler.foundation.serialization.Serializer;
import java.util.Map;
import org.prevayler.implementation.publishing.TransactionPublisher;
import java.io.IOException;
public class PrevaylerFactory {
    @Stub
    public org.prevayler.implementation.publishing.censorship.TransactionCensor censor(org.prevayler.implementation.snapshot.GenericSnapshotManager snapshotManager) {
        return null;
    }
    @Stub
    public org.prevayler.foundation.network.OldNetwork network() {
        return null;
    }
    @Stub
    public org.prevayler.Clock clock() {
        return null;
    }
    @Stub
    public java.util.Map _snapshotSerializers;
    @Stub
    public java.lang.String prevalenceDirectory() {
        return null;
    }
    @Stub
    public int _remoteServerPort;
    @Stub
    public org.prevayler.implementation.journal.Journal journal() {
        return null;
    }
    @Stub
    public java.lang.String _remoteServerIpAddress;
    @Stub
    public org.prevayler.foundation.monitor.Monitor monitor() {
        return null;
    }
    @Stub
    public static void original(org.prevayler.PrevaylerFactory factory) {
        return ;
    }
    @Stub
    public org.prevayler.foundation.serialization.Serializer journalSerializer() {
        return null;
    }
    @Stub
    public long _journalSizeThreshold;
    @Stub
    public org.prevayler.implementation.publishing.TransactionPublisher publisher() {
        return null;
    }
    @Stub
    public java.lang.String _primarySnapshotSuffix;
    @Stub
    public long _journalAgeThreshold;
    @Stub
    public void configurePrevalentSystem(java.lang.Object newPrevalentSystem) {
        return ;
    }
    @Stub
    public java.lang.Object prevalentSystem() {
        return null;
    }
    @Stub
    public void configurePrevalenceDirectory(java.lang.String prevalenceDirectory) {
        return ;
    }
    @Stub
    public org.prevayler.Prevayler create() {
        return null;
    }
    @Stub
    public java.lang.String journalSuffix() {
        return null;
    }
    @Stub
    public static void original(java.io.Serializable newPrevalentSystem, org.prevayler.PrevaylerFactory factory) {
        return ;
    }
    public static class PrevaylerFactory_journal {
        @Stub
        public void original() {
            return ;
        }
        @Stub
        public org.prevayler.PrevaylerFactory _this;
        @Stub
        public org.prevayler.implementation.PrevaylerDirectory directory;
    }
    public static class PrevaylerFactory_create {
        @Stub
        public org.prevayler.implementation.snapshot.GenericSnapshotManager snapshotManager;
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
