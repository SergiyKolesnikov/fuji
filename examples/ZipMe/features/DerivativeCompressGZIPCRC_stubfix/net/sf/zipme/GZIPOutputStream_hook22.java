package net.sf.zipme;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
import net.sf.zipme.CRC32;
import net.sf.zipme.GZIPOutputStream;
public class GZIPOutputStream_hook22 {
    @Stub
    public void original() {
        return ;
    }
    @Stub
    public net.sf.zipme.GZIPOutputStream _this;
    @Stub
    public int crcval;
    @Stub
    public byte[] gzipFooter;
}
