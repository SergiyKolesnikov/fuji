package org.prevayler.implementation;

import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
import java.lang.Object;
import java.lang.Exception;
import org.prevayler.Transaction;
import org.prevayler.Clock;
import java.lang.ClassNotFoundException;
import org.prevayler.Prevayler;
import org.prevayler.implementation.Capsule;
import java.lang.RuntimeException;
import org.prevayler.implementation.TransactionWithQueryCapsule;
import org.prevayler.TransactionWithQuery;
import org.prevayler.foundation.serialization.Serializer;
import org.prevayler.implementation.TransactionCapsule;
import org.prevayler.SureTransactionWithQuery;
import org.prevayler.implementation.publishing.TransactionPublisher;
import java.io.IOException;

public class PrevaylerImpl implements Prevayler {
	@Stub
	public PrevaylerImpl(TransactionPublisher transactionPublisher,
			Serializer journalSerializer) throws IOException,
			ClassNotFoundException {
	}
	@Stub
	public Object execute(Query sensitiveQuery) throws Exception {
		return null;
	}
	@Stub
	public void takeSnapshot() throws IOException {
	}
	@Stub
	public Object prevalentSystem() {
		return null;
	}
}
