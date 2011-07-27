package org.prevayler.demos.demo2.business;
import de.ovgu.cide.jakutil.*;
public interface BankListener {
  public void accountCreated(  Account account);
  public void accountDeleted(  Account account);
}
