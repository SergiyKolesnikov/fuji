
package EncryptedChat;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



abstract class ChatProtocol$$Chat {
	
	private final static List messageList = Collections.synchronizedList(new LinkedList());
	private long lastLineTime;
	
	public ChatProtocol$$Chat() {
		lastLineTime = new Date().getTime();
	}

	private final static Pattern rxLine = Pattern.compile("<(.*?)>(.*)");
	
	private String username = null;
	private boolean authorized = true;
	private boolean quit = false;
	
	public final static String USERNAME_PASSWORD_SEP = ",";
	public final static String ERROR_STRING = ">ERR<";
	public final static String OK_STRING = ">OK<";
	public final static String MESSAGE_PREFIX = " ";
	
	public String preProcess(String inputLine) {
		return inputLine;
	}
	
	protected String returnE(String s) {
		return s;
	}
	
	protected String handleSpecialAction(String action, String value) {
		return null;
	}
	
	protected void postMessageSend(String username, String value) {
	}
	
	public String processInput(String inputLine) throws ProtocolException {
		System.out.println("received " + inputLine);
		
		Matcher m = null;
		synchronized (rxLine) {
			m = rxLine.matcher(inputLine);
		}
		
		if (!m.find()) {
			throw new ProtocolException(inputLine);	
		}
		
		quit = false;
		
		final String action = m.group(1).toLowerCase();
		final String value = m.group(2);
		String specActionRes = handleSpecialAction(action, value);
		if (specActionRes != null) {
			return specActionRes;
		} else if (action.equals("quit")) {
			quit = true;
			return returnE(OK_STRING);
		} else if (authorized) {
			if (action.equals("send")) {
				final ChatLine line = new ChatLine(username, value, new Date().getTime());
				messageList.add(line);
				postMessageSend(username, value);
				
				return returnE(OK_STRING);
			} else if (action.equals("get")) {
				final StringBuffer toSend = new StringBuffer();
				synchronized(messageList) {
					ChatLine l = null;
					for (Iterator it = messageList.iterator(); it.hasNext(); l = (ChatLine) it.next()) {
						if (l.getTime() > lastLineTime /*&& !l.getUser().equals(username)*/) {
							toSend.append(l.getUser() + ": " + l.getLine() + "\\n");
							lastLineTime = l.getTime();
						}
					}
				}
				return returnE(MESSAGE_PREFIX + toSend.toString());
			}
		}
		return returnE(ERROR_STRING);
	}
	
	public boolean isQuit() {
		return quit;
	}

}



public class ChatProtocol extends  ChatProtocol$$Chat  {
	private LinkedList encryption = new LinkedList();

	public static String decrypt(LinkedList enc, String s) {
		Iterator it = enc.descendingIterator();
		while (it.hasNext()) {
			TransportEncryption e = (TransportEncryption) it.next();
			s = e.decrypt(s);
		}
		return s;
	}
	
	protected String returnE(String s) {
		Iterator it = encryption.iterator();
		TransportEncryption e = null;
		for ( ; it.hasNext(); e = (TransportEncryption) it.next()) {
			s = e.encrypt(s);
		}
		return s;
	}
	
	protected String handleSpecialAction(String action, String value) {
		if (action.equals("crypto")) {
			TransportEncryption e = EncryptionFactory.getFactory().getEncryption(value);
			if (e != null) {
				String res = returnE(OK_STRING);
				if (encryption.contains(e)) {
					encryption.remove(e);
					System.out.println("removed crypto " + e.getName());
				} else {
					encryption.add(e);
					System.out.println("installed crypto " + e.getName());
				}
				return res;
			}
		}
		return null;
	}
	
	public String preProcess(String inputLine) {
		inputLine = decrypt(encryption, inputLine);
		/*
		try {
			log.log(inputLine + "\n");
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
		*/
		return inputLine;
	}
      // inherited constructors


	
	public ChatProtocol (  ) { super(); }
}