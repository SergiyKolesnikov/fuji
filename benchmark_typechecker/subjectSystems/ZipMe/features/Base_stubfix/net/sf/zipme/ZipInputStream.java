package net.sf.zipme;
import java.io.InputStream;

import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public class ZipInputStream extends InflaterInputStream implements net.sf.zipme.ZipConstants {
	@Stub
	public ZipInputStream(InputStream in) {
		super(in, new Inflater(true));
	}
//    @Stub
//    public int ENDCOM;
//    @Stub
//    public int LOCNAM;
//    @Stub
//    public int CENDSK;
//    @Stub
//    public long LOCSIG;
//    @Stub
//    public long CENSIG;
//    @Stub
//    public int EXTSIZ;
//    @Stub
//    public int ENDOFF;
//    @Stub
//    public int CENTIM;
//    @Stub
//    public int LOCTIM;
//    @Stub
//    public int CENVEM;
//    @Stub
//    public int CENNAM;
//    @Stub
//    public int EXTHDR;
//    @Stub
//    public int LOCCRC;
//    @Stub
//    public int CENEXT;
//    @Stub
//    public int LOCEXT;
//    @Stub
//    public int CENFLG;
//    @Stub
//    public int LOCVER;
//    @Stub
//    public long ENDSIG;
//    @Stub
//    public int CENHOW;
//    @Stub
//    public int CENATT;
//    @Stub
//    public int CENCOM;
//    @Stub
//    public int LOCLEN;
//    @Stub
//    public int CENOFF;
//    @Stub
//    public int CENATX;
//    @Stub
//    public int LOCFLG;
//    @Stub
//    public int LOCHDR;
//    @Stub
//    public int CENHDR;
//    @Stub
//    public int LOCHOW;
//    @Stub
//    public long EXTSIG;
//    @Stub
//    public int CENVER;
//    @Stub
//    public int ENDSIZ;
//    @Stub
//    public int LOCSIZ;
//    @Stub
//    public int CENSIZ;
//    @Stub
//    public int ENDSUB;
//    @Stub
//    public int ENDHDR;
//    @Stub
//    public int EXTLEN;
//    @Stub
//    public int CENLEN;
//    @Stub
//    public int ENDTOT;
//    @Stub
//    public int CENCRC;
//    @Stub
//    public int EXTCRC;
	
}
