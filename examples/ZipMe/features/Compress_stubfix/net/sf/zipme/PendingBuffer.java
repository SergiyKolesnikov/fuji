package net.sf.zipme;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public class PendingBuffer {
    @Stub
    public boolean isFlushed() {
        return true;
    }
    @Stub
    public void writeBits(int b, int count) {
        return ;
    }
    @Stub
    public int getBitCount() {
        return 0;
    }
    @Stub
    public void writeShortMSB(int s) {
        return ;
    }
    @Stub
    public void alignToByte() {
        return ;
    }
    @Stub
    public int flush(byte[] output, int offset, int length) {
        return 0;
    }
	@Stub
	public PendingBuffer(int bufsize) {
	}
}
