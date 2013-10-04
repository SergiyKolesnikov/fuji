package net.sf.zipme;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
import java.lang.String;
import net.sf.zipme.ZipException;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import net.sf.zipme.ZipEntry;
import java.io.UnsupportedEncodingException;
import java.io.OutputStream;
import net.sf.zipme.ZipConstants;
import java.lang.Error;
import java.lang.Throwable;
import java.lang.System;
import java.util.Enumeration;
import net.sf.zipme.DeflaterOutputStream;
import net.sf.zipme.Deflater;
import java.util.Vector;
import java.io.IOException;
public class ZipOutputStream extends net.sf.zipme.DeflaterOutputStream implements net.sf.zipme.ZipConstants {
	@Stub
	public static final int STORED = 0;
	@Stub
	public static final int DEFLATED = 8;
}
