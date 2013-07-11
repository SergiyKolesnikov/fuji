package net.sf.zipme;
import java.io.InputStream;

import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public class InflaterInputStream extends InputStream {
    @Stub
    public void close() throws java.io.IOException {
        return ;
    }
    @Stub
    public int read(byte[] b, int off, int len)  throws java.io.IOException {
        return 0;
    }
    @Stub
    public net.sf.zipme.Inflater inf;
    @Stub
    public java.io.InputStream in;
    @Stub
    public byte[] buf;
    @Stub
    public int len;
    @Stub
    public int read(byte[] buf)  throws java.io.IOException {
        return 0;
    }
    @Stub
    public int read() throws java.io.IOException {
    	return 0;
    }
    @Stub
	public InflaterInputStream(InputStream in, Inflater inf) {
		this(in, inf, 0);
	}
	@Stub
	public InflaterInputStream(InputStream in, Inflater inf, int size) {
	}
}
