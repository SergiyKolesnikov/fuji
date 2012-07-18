package org.prevayler.implementation.publishing.censorship;
import org.prevayler.implementation.TransactionTimestamp;
import de.ovgu.cide.jakutil.*;
public interface TransactionCensor {
  public void approve(  TransactionTimestamp transactionTimestamp) throws RuntimeException, Error ;
}
