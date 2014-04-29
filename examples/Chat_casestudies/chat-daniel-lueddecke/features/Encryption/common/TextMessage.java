package common;

import encryption.EncryptionType;
import encryption.ROT13;
import encryption.SWITCH;

/**
 * serializable message that can be send over the sockets between client and
 * server. 
 */
public class TextMessage implements Serializable {
	
	private EncryptionType crypto = EncryptionType.ROT13;
	
	public String getContent() {
		return decryptMessage(this.content);
	}
	
	public void setContent(String content) {
		this.content = encryptMessage(content);
	}
	
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