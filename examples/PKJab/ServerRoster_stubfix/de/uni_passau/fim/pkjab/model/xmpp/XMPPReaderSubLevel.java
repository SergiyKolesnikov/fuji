package de.uni_passau.fim.pkjab.model.xmpp;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public class XMPPReaderSubLevel  extends XMPPReaderAdapter {
    @Stub
    public static java.lang.String ROSTER_URI;
    @Stub
    protected boolean startChild(de.uni_passau.fim.pkjab.util.Stack xmlStack, de.uni_passau.fim.pkjab.model.tags.AbstractXMLTag thisTag, org.xml.sax.Attributes atts)  throws org.xml.sax.SAXException {
        return true;
    }
	@Stub
    protected XMPPReaderSubLevel(final XMPPReaderAdapter previousHandler,
			final String namespace) {
	}
}
