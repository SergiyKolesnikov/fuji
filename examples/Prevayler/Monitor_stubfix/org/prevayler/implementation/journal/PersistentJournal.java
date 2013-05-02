package org.prevayler.implementation.journal;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
import java.lang.String;
import java.lang.Object;
import org.prevayler.implementation.PrevaylerDirectory;
import org.prevayler.foundation.DurableInputStream;
import java.io.File;
import org.prevayler.foundation.monitor.Monitor;
import java.io.IOException;
public class PersistentJournal implements org.prevayler.implementation.journal.Journal {
    @Stub
    public void original(java.io.IOException iox, java.io.File journal, java.lang.String message) {
        return ;
    }
    @Stub
    public Object original(java.io.File journal, org.prevayler.foundation.DurableInputStream input) {
        return null;
    }
    @Stub
    public PersistentJournal(  PrevaylerDirectory directory,  long journalSizeThresholdInBytes,  long journalAgeThresholdInMillis,  String journalSuffix) throws IOException {
      }
}
