package common; 

import java.io.Serializable; 

import encryption.EncryptionType; 
import encryption.ROT13; 
import encryption.SWITCH; 

/**
 * serializable message that can be send over the sockets between client and
 * server. 
 */
public   class  TextMessage  implements Serializable {
	

	private static final long serialVersionUID = -9161595018411902079L;

	
	private String content;

	
	private String source = null;

	
	
	public TextMessage(String content, String source) {
		super();
		this.setContent(content);
		this.source = source;
	}

	
	
	 public void setContent  (String content) {
		this.content = encryptMessage(content);
	}

	
	
	public String getContent  () {
		return decryptMessage(this.content);
	}

	
	
	public String getSource() {
		return this.source;
	}

	
	
	public void setSource(String source) {
		this.source = source;
	}

	
	
	private EncryptionType crypto = EncryptionType.ROT13;

	
	
	private String encryptMessage(String text) {
		
		switch (crypto) {
		case ROT13:
			return ROT13.encrypt(text);
		case SWITCH:
			return SWITCH.encrypt(text);

		default:
			return text;
		}
	}

	
	
	
	private String decryptMessage(String text) {		
		switch (crypto) {
		case ROT13:
			return ROT13.decrypt(text);
		case SWITCH:
			return SWITCH.decrypt(text);

		default:
			return text;
		}
	}


}
