package com.sleepycat.je.txn;
public class Txn {
@MethodObject class Txn_traceCommit {
    void execute(){
      logger=envImpl.getLogger();
      original();
    }
  }
}
