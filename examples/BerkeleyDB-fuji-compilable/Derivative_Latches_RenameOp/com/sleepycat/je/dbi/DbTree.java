package com.sleepycat.je.dbi;
public class DbTree {
  protected void hook298(  CursorImpl nameCursor) throws DatabaseException {
    nameCursor.releaseBIN();
    original(nameCursor);
  }
}
