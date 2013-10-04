package org.prevayler.implementation.publishing;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
import org.prevayler.implementation.TransactionTimestamp;
import org.prevayler.implementation.publishing.censorship.TransactionCensor;
import org.prevayler.implementation.journal.Journal;
import org.prevayler.Clock;
public class CentralPublisher extends org.prevayler.implementation.publishing.AbstractPublisher {
    @Stub
    public void original(org.prevayler.implementation.TransactionTimestamp timestamp) {
        return ;
    }
    @Stub
    public CentralPublisher(  Clock clock,  Journal journal){
        super(null);
      }
}
