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
	private String empfaenger;

	public TextMessage() {}
	
	public TextMessage(String content) {
		super();
		this.content = content;
		this.sender = null;
		this.empfaenger = null;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getEmpfaenger() {
		return empfaenger;
	}

	public void setEmpfaenger(String empfaenger) {
		this.empfaenger = empfaenger;
	}

}
