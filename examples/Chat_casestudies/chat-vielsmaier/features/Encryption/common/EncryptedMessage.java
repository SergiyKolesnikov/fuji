package common;

public class EncryptedMessage extends TextMessage {
	public EncryptedMessage(String ifrom, String itext, CryptoProvider iprovider) {
		super(ifrom, iprovider.encrypt(itext));
	}

	private static final long serialVersionUID = 5163851577163847127L;
	
	public TextMessage decrypt(CryptoProvider provider) {
		return new TextMessage(getFrom(), provider.decrypt(super.getText()));
	}
	
}
