package net.sf.zipme;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public interface Checksum {
    @Stub
    void update(int bval);
    @Stub
    void update(byte[] buf, int off, int len);
    @Stub
    long getValue();
    @Stub
    void reset();
}
