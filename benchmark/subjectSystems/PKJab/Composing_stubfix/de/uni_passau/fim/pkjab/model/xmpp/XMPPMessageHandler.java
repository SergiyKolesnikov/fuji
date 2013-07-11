package de.uni_passau.fim.pkjab.model.xmpp;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
import java.lang.String;
import de.uni_passau.fim.pkjab.model.tags.AbstractXMLTag;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import de.uni_passau.fim.pkjab.model.Contact;
import de.uni_passau.fim.pkjab.util.ChatState;
import de.uni_passau.fim.pkjab.util.Stack;
public class XMPPMessageHandler extends de.uni_passau.fim.pkjab.model.xmpp.XMPPReaderIgnore {
    @Stub
    public de.uni_passau.fim.pkjab.model.Contact contact;
    @Stub
    public boolean original(de.uni_passau.fim.pkjab.util.Stack xmlStack, de.uni_passau.fim.pkjab.model.tags.AbstractXMLTag thisTag, org.xml.sax.Attributes atts) {
        return true;
    }
    @Stub
    public boolean original(de.uni_passau.fim.pkjab.util.Stack xmlStack, de.uni_passau.fim.pkjab.model.tags.AbstractXMLTag thisTag, java.lang.String content) {
        return true;
    }
}
