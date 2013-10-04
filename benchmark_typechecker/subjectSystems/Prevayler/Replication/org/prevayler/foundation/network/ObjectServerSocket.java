package org.prevayler.foundation.network;
import java.io.IOException;
import de.ovgu.cide.jakutil.*;
public interface ObjectServerSocket {
  ObjectSocket accept() throws IOException ;
  void close() throws IOException ;
}
