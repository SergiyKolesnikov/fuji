package org.prevayler.demos.scalability;
import java.util.List;
import de.ovgu.cide.jakutil.*;
public interface QueryConnection {
  /** 
 * Returns the List of all Record with the given name.
 */
  public List queryByName(  String name);
}
