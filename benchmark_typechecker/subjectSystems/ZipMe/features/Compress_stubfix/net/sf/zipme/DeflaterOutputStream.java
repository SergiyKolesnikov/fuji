package net.sf.zipme;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
import java.lang.Error;
import java.io.OutputStream;
import net.sf.zipme.Deflater;
import java.io.IOException;
public class DeflaterOutputStream extends java.io.OutputStream {
    @Stub
    public java.io.OutputStream out;
    @Stub
    public byte[] buf;
    @Stub
    public net.sf.zipme.Deflater def;
}
