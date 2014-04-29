package common;

import java.io.Serializable;

/**
 * serializable message that can be send over the sockets between client and
 * server. 
 */
public class TextMessage implements Serializable {

	private static final long serialVersionUID = -9161595018411902079L;
	private String content;
	private String sender;

	public TextMessage(String content) {
		super();
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	/**
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * @param sender the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}
