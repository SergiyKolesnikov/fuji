package org.prevayler.implementation.clock;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public class BrokenClock implements org.prevayler.Clock {
    @Stub
    public java.util.Date time() {
        return null;
    }
    @Stub
    public void advanceTo(java.util.Date newTime) {
        return ;
    }
    @Stub
    public BrokenClock() {
    }
}
