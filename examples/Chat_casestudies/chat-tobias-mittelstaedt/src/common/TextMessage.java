package common;

import java.io.Serializable;

import tools.Encryption;

/**
 * serializable message that can be send over the sockets between client and
 * server.
 */
public class TextMessage implements Serializable {

	private static final long serialVersionUID = -9161595018411902079L;
	private String content;
	private String encryptionSelector = "NONE";

	public TextMessage(String content) {
		super();

	}
	
	public TextMessage(String content, String encryptionSelector) {
		super();
		this.setEncryptionSelector(encryptionSelector);
		
	}

	public String getContent() {
		return content;
	}

	public String getEncryptionSelector() {
		return encryptionSelector;
	}

	public void setEncryptionSelector(String encryptionSelector) {
		this.encryptionSelector = encryptionSelector;
	}
	
	public String decryptContent() {
		if (this.getEncryptionSelector().equals(Encryption.NONE)) {
			return this.content;
		} else if (this.getEncryptionSelector().equals(Encryption.REVERSAL)) {
			return Encryption.reverseMessage(this);
		} else {
			return Encryption.rot13(this);
		}
	}
}
