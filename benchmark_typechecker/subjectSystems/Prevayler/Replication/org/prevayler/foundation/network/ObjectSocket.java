package org.prevayler.foundation.network;
import java.io.IOException;
import de.ovgu.cide.jakutil.*;
public interface ObjectSocket {
  void writeObject(  Object object) throws IOException ;
  Object readObject() throws IOException, ClassNotFoundException ;
  void close() throws IOException ;
}
