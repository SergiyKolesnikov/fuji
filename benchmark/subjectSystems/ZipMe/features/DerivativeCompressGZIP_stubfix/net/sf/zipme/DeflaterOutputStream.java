package net.sf.zipme;

import java.io.IOException;
import java.io.OutputStream;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public class DeflaterOutputStream extends OutputStream {
    @Stub
    public void write(int b) throws IOException{
    	return ;
    }
    @Stub
    public void finish() throws IOException {
    	return ;
    }
    @Stub
    protected OutputStream out;
    @Stub
    protected Deflater def;
    @Stub
    protected byte[] buf;
    @Stub
	public DeflaterOutputStream(OutputStream out, Deflater defl) {
		this(out, defl, 0);
	}
    @Stub
	public DeflaterOutputStream(OutputStream out, Deflater defl, int bufsize) {
	}
}