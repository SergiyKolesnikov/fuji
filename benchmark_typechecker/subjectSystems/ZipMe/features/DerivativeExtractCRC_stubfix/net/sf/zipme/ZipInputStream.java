package net.sf.zipme;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
import net.sf.zipme.ZipException;
import net.sf.zipme.CRC32;
import net.sf.zipme.InflaterInputStream;
import net.sf.zipme.ZipEntry;
import java.io.IOException;
import net.sf.zipme.ZipConstants;
public class ZipInputStream extends net.sf.zipme.InflaterInputStream implements net.sf.zipme.ZipConstants {
    @Stub
    public void original(byte[] b, int off, int len) {
        return ;
    }
    @Stub
    public net.sf.zipme.ZipEntry entry;
    @Stub
    public Object original() {
        return null;
    } 
}
