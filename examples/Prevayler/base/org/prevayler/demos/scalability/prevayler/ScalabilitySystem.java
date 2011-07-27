package org.prevayler.demos.scalability.prevayler;
import org.prevayler.demos.scalability.*;
import de.ovgu.cide.jakutil.*;
interface ScalabilitySystem extends java.io.Serializable {
  void replaceAllRecords(  RecordIterator newRecords);
}
