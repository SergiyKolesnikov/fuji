package org.prevayler.foundation.network;
import de.ovgu.cide.jakutil.*;
public interface Service {
  ObjectReceiver serverFor(  ObjectReceiver client);
}
