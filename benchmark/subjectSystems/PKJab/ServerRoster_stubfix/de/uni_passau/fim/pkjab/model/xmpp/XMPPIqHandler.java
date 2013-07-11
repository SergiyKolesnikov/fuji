package de.uni_passau.fim.pkjab.model.xmpp;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
import de.uni_passau.fim.pkjab.model.tags.AbstractXMLTag;
import de.uni_passau.fim.pkjab.model.ConnectionCallback;
import org.xml.sax.SAXException;
import de.uni_passau.fim.pkjab.model.xmpp.XMPPReaderSubLevel;
import de.uni_passau.fim.pkjab.model.tags.Iq;
import org.xml.sax.Attributes;
import de.uni_passau.fim.pkjab.model.tags.XMLTag;
import de.uni_passau.fim.pkjab.model.xmpp.XMPPRosterHandler;
import de.uni_passau.fim.pkjab.model.xmpp.XMPPReaderAdapter;
import de.uni_passau.fim.pkjab.util.Stack;
public class XMPPIqHandler extends XMPPReaderSubLevel  {
	protected XMPPIqHandler(XMPPReaderAdapter previousHandler) {
		super(previousHandler, null);
	}
}
