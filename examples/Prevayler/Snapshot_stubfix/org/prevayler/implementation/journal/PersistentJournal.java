package org.prevayler.implementation.journal;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public class PersistentJournal implements org.prevayler.implementation.journal.Journal {
    @Stub
    public PersistentJournal(org.prevayler.implementation.PrevaylerDirectory directory, long journalSizeThresholdInBytes, long journalAgeThresholdInMillis, java.lang.String journalSuffix) {
    }
    @Stub
    public PersistentJournal(org.prevayler.implementation.PrevaylerDirectory directory, long journalSizeThresholdInBytes, long journalAgeThresholdInMillis, java.lang.String journalSuffix, org.prevayler.foundation.monitor.Monitor monitor) {
    }
}
