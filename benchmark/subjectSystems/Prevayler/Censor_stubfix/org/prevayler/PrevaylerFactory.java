package org.prevayler;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
import org.prevayler.implementation.publishing.censorship.StrictTransactionCensor;
import org.prevayler.implementation.publishing.censorship.TransactionCensor;
import org.prevayler.implementation.publishing.censorship.LiberalTransactionCensor;
import org.prevayler.implementation.snapshot.GenericSnapshotManager;
public class PrevaylerFactory {
    @Stub
    public boolean _transactionFiltering;
}
