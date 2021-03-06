package common;

import java.util.StringTokenizer;

/**
 * represents Encrypted Message
 * added: 13.05
 */

public class EncryptedMessage extends Message {

	private static final long serialVersionUID = 5163851577163847127L;

	private String encryptedMessage;
	
	//constructor
	public EncryptedMessage() {
	}
	
	public void setEncryptedMessage(String message){
		encryptedMessage = message;
	}
	
	public String getEncryptedMessage() {
		
		return encryptedMessage;
	}

}
