package org.prevayler.implementation.journal;
import org.prevayler.implementation.TransactionGuide;
import org.prevayler.implementation.publishing.TransactionSubscriber;
import java.io.IOException;
import de.ovgu.cide.jakutil.*;
public interface Journal {
  public void append(  TransactionGuide guide);
  public void update(  TransactionSubscriber subscriber,  long initialTransaction) throws IOException, ClassNotFoundException ;
  public void close() throws IOException ;
  public long nextTransaction();
}
