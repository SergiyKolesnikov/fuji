package client; 

import common.TextMessage; 

public   class  Message {
	
	public static final int
		NUM_SENDERTYPES = 4,
		SENDERTYPE_DEFATULT = 0,
		SENDERTYPE_SELF = 1,
		SENDERTYPE_OTHER = 2,
		SENDERTYPE_SERVER = 3;

	

	private final String sender;

	
	private final int sendertype;

	
	
	private String content;

	

	public Message(String username, TextMessage m) {
		content = m.getContent();
		sender = m.getSender();
		
		if (sender != null) {
			if (sender.equals(username))
				sendertype = SENDERTYPE_SELF;
			else if (sender.equals(TextMessage.SERVER_MESSAGE))
				sendertype = SENDERTYPE_SERVER;
			else 
				sendertype = SENDERTYPE_OTHER;
		} else {
			sendertype = SENDERTYPE_DEFATULT;
		}
	}

	

	public static Message info(String infoString) {
		return new Message(infoString);
	}

	

	private Message(String content) {
		this.content = content;
		sender = null;
		sendertype = SENDERTYPE_DEFATULT;
	}

	

	public String getLine() {
		if (sender != null) {
			return sender + " > " + content;
		} else {
			return content;
		}
	}

	

	public int getSenderType() {
		return sendertype;
	}

	

	public String getContent() {
		return content;
	}

	
	
	public String getSender() {
		return sender;
	}

	
	
	public void setContent(String content) {
		this.content = content;
	}

	
	private boolean valid = true;

	
	
	public boolean isValid() {
		return valid;
	}

	

	public void setInvalid() {
		valid = false;
	}


}
