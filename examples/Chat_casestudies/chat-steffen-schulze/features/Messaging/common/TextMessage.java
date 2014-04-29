package common;

import java.io.Serializable;

/**
 * serializable message that can be send over the sockets between client and
 * server. 
 */
public class TextMessage implements Serializable {
	private String receiver;
	
	public TextMessage(String sender, String line, String receiver)
	{
		this(sender, line);
		this.receiver = receiver;
	}
	
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
}
