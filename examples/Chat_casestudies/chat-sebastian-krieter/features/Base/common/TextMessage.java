package common;

import java.io.Serializable;

/**
 * serializable message that can be send over the sockets between client and
 * server. 
 */
public class TextMessage implements Serializable {
	
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
}