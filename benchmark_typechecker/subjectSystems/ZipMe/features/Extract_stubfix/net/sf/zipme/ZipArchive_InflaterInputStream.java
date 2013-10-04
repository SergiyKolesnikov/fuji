package net.sf.zipme;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public class ZipArchive_InflaterInputStream extends InflaterInputStream {
    @Stub
    public ZipArchive_InflaterInputStream(java.io.InputStream in, net.sf.zipme.Inflater inf, int sz) {
    	super(in,inf);
    }
}
