package common; 

import java.io.Serializable; 

/**
 * serializable message that can be send over the sockets between client and
 * server. 
 */
public   class  TextMessage  implements Serializable {
	

	private static final long serialVersionUID = -9161595018411902079L;

	
	private String content;

	
	private String sender;

	
	
	public TextMessage(String sender, String content) {
		super();
		this.content = content;
		this.sender = sender;
	}

	

	public String getContent() {
		return content;
	}

	

	public String getSender() {
		return sender;
	}

	
	
	public void setSender(String sender)
	{
		this.sender = sender;
	}

	
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
