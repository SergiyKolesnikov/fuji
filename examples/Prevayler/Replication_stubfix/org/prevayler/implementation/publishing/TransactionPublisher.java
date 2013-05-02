package org.prevayler.implementation.publishing;

import java.io.IOException;
import org.prevayler.implementation.Capsule;
import org.prevayler.Clock;

import de.uni_passau.spl.bytecodecomposer.stubs.Stub;

public interface TransactionPublisher {
	@Stub
	public void subscribe(TransactionSubscriber subscriber,
			long initialTransaction) throws IOException, ClassNotFoundException;

	@Stub
	public void cancelSubscription(TransactionSubscriber subscriber);

	@Stub
	public void publish(Capsule capsule);

	@Stub
	public Clock clock();
}
