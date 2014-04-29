package common;

import java.io.Serializable;

/**
 * serializable message that can be send over the sockets between client and
 * server.
 */
public class TextMessage implements Serializable {

	private String encryptionType;

	public TextMessage(String content) {
		this.encryptionType = null;
	}


	public String getEncryptionType() {
		return encryptionType;
	}

	public void setEncryptionType(String encryptionType) {
		this.encryptionType = encryptionType;
	}
}
