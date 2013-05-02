package net.sf.zipme;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
import java.lang.String;
import net.sf.zipme.ZipException;
import java.io.ByteArrayInputStream;
import net.sf.zipme.ZipArchive_InflaterInputStream;
import java.util.Hashtable;
import net.sf.zipme.ZipEntry;
import java.io.InputStream;
import net.sf.zipme.Inflater;
import net.sf.zipme.ZipOutputStream;
import net.sf.zipme.ZipArchive_PartialInputStream;
import java.io.IOException;
import net.sf.zipme.ZipConstants;
public class ZipArchive implements net.sf.zipme.ZipConstants {
    @Stub
    public byte[] buf;
    @Stub
    public int len;
    @Stub
    public java.util.Hashtable getEntries() {
        return null;
    }
    @Stub
    public int off;
//    @Stub
//    public long CENSIG;
//    @Stub
//    public long LOCSIG;
//    @Stub
//    public long EXTSIG;
}
