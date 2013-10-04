package net.sf.zipme;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
import java.io.EOFException;
import java.lang.System;
import net.sf.zipme.CRC32;
import net.sf.zipme.InflaterInputStream;
import java.io.InputStream;
import net.sf.zipme.Inflater;
import net.sf.zipme.Deflater;
import java.io.IOException;
public class GZIPInputStream extends net.sf.zipme.InflaterInputStream {
	@Stub
	public GZIPInputStream(InputStream in, int size) throws IOException {
		super(in, null, size);
	  }

}
