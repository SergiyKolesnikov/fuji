package org.prevayler.demos.scalability;
import de.ovgu.cide.jakutil.*;
public interface TransactionConnection {
  public void performTransaction(  Record recordToInsert,  Record recordToUpdate,  long idToDelete);
}
