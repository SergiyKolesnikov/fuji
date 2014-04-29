package common; 

import java.io.Serializable; 

/**
 * TODO description
 */
public   class  TextMessage  implements Serializable {
	

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

	
	private String color;

	
	
	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	

	

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	
	private String reciever;

	
	public boolean privatMessage = false;

	
	
	
	public String getReciever() {
		return reciever;
	}

	

	public void setReciever(String reciever) {
		this.privatMessage = true;
		this.reciever = reciever;
	}


}
