package net.sf.zipme;

import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
import net.sf.zipme.GZIPInputStream;
import java.lang.System;
import net.sf.zipme.CRC32;
import net.sf.zipme.GZIPOutputStream_hook22;
import java.io.OutputStream;
import net.sf.zipme.DeflaterOutputStream;
import net.sf.zipme.Deflater;
import java.io.IOException;

public class GZIPOutputStream extends DeflaterOutputStream {
	@Stub
	public GZIPOutputStream(OutputStream out) throws IOException {
		this(out, 0);
	}
	@Stub
	public GZIPOutputStream(OutputStream out, int size) throws IOException {
		super(out, null, size);
	}
}
