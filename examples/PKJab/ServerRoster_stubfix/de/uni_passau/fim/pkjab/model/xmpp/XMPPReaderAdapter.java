package de.uni_passau.fim.pkjab.model.xmpp;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
public class XMPPReaderAdapter implements org.xml.sax.ContentHandler {
    @Stub
    public de.uni_passau.fim.pkjab.model.ConnectionCallback connection;
    @Stub
    public boolean startElement(de.uni_passau.fim.pkjab.util.Stack xmlStack, de.uni_passau.fim.pkjab.model.tags.AbstractXMLTag thisTag, de.uni_passau.fim.pkjab.model.tags.AbstractXMLTag lastTag, org.xml.sax.Attributes atts) {
        return true;
    }
    @Stub
	public void characters(char[] ch, int start, int length)
			throws SAXException {
	}
    @Stub
	public void startDocument() throws SAXException {
	}	
    @Stub
	public void endDocument() throws SAXException {
	}
    @Stub
    public void endPrefixMapping(String prefix) throws SAXException {
	}
    @Stub
	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
	}
    @Stub
	public void processingInstruction(String target, String data)
			throws SAXException {
	}
    @Stub
	public void setDocumentLocator(Locator locator) {
	}
    @Stub
	public void skippedEntity(String name) throws SAXException {
	}
    @Stub
	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
	}
    @Stub    
    public void startElement(String uri, String localName, String name,
			Attributes atts) throws SAXException {
	}
    @Stub    
    public void endElement(String uri, String localName, String name)
			throws SAXException {
    }
		
    
}
