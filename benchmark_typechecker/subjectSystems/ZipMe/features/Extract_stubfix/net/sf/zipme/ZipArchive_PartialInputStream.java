package net.sf.zipme;
import java.io.ByteArrayInputStream;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public class ZipArchive_PartialInputStream extends ByteArrayInputStream {
    @Stub
    public int readLeShort() {
        return 0;
    }
    @Stub
    public void addDummyByte() {
        return ;
    }
    @Stub
    public ZipArchive_PartialInputStream(byte[] buffer, int offset, int length) {
    	super(buffer,offset,length);
    }
    @Stub
    public void setLength(int length) {
        return ;
    }
    @Stub
    public void seek(int newpos) {
        return ;
    }
    @Stub
    public int readLeInt() {
        return 0;
    }
}
