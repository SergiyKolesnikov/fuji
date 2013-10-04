package org.prevayler.implementation.publishing;
import org.prevayler.implementation.TransactionTimestamp;
import de.ovgu.cide.jakutil.*;
public interface TransactionSubscriber {
  public void receive(  TransactionTimestamp transactionTimestamp);
}
