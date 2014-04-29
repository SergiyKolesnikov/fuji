package common; 

import java.io.Serializable; 

public   class  TextMessage  implements Serializable {
	
	
	public static final String
		SERVER_MESSAGE = "Server";

	
	
	private static final long serialVersionUID = -9161595018411902079L;

	
	
	private String content, sender;

	
	
	public TextMessage(String sender, String content) {
		this.content = content;
		this.sender = sender;
	}

	
	
	public void setSender(String sender) {
		this.sender = sender;
	}

	
	
	public void setContent(String content) {
		this.content = content;
	}

	
	
	public String getContent() {
		return content;
	}

	
	
	public String getSender() {
		return sender;
	}

	
	private String receiver = null;

	

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	
	
	public String getReceiver() {
		return receiver;
	}


}
