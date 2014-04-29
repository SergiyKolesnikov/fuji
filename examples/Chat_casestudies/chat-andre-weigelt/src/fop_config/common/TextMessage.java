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

	
	private String empfaenger;

	

	public TextMessage() {}

	

	public TextMessage  (String content) {
		super();
		this.content = content;
		this.sender = null;
		this.empfaenger = null;
	
		this.color = null;
	
		this.encryptionType = null;
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

	

	private String color;

	


	public String getColor() {
		return color;
	}

	

	public void setColor(String color) {
		this.color = color;
	}

	

	private String encryptionType;

	


	public String getEncryptionType() {
		return encryptionType;
	}

	

	public void setEncryptionType(String encryptionType) {
		this.encryptionType = encryptionType;
	}


}
