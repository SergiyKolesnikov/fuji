package org.prevayler.foundation.network;
import de.ovgu.cide.jakutil.*;
/** 
 * Useful class comments should go here
 * $Revision: 1.1 $
 * $Date: 2005/03/02 06:04:17 $
 * $Author: peter_mxgroup $
 */
public interface SessionsManager {
  StubbornNetworkProxy find(  NetworkSessionId sessionId);
  NetworkSessionId add(  StubbornNetworkProxy receiver);
  void remove(  NetworkSessionId sessionId);
}
