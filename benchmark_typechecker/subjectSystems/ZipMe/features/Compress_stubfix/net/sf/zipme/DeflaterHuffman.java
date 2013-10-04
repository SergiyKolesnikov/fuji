package net.sf.zipme;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public class DeflaterHuffman {
    @Stub
    public void reset() {
        return ;
    }
    @Stub
    public boolean tallyDist(int dist, int len) {
        return true;
    }
    @Stub
    public boolean isFull() {
        return true;
    }
    @Stub
    public void flushBlock(byte[] stored, int stored_offset, int stored_len, boolean lastBlock) {
        return ;
    }
    @Stub
    public DeflaterHuffman(net.sf.zipme.DeflaterPending pending) {
    }
    @Stub
    public boolean tallyLit(int lit) {
        return true;
    }
    @Stub
    public void flushStoredBlock(byte[] stored, int stored_offset, int stored_len, boolean lastBlock) {
        return ;
    }
}
