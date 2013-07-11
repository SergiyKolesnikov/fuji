package org.prevayler.foundation.network;
import java.io.IOException;
import de.ovgu.cide.jakutil.*;
public interface OldNetwork {
  ObjectSocket openSocket(  String serverIpAddress,  int serverPort) throws IOException ;
  ObjectServerSocket openObjectServerSocket(  int port) throws IOException ;
}
