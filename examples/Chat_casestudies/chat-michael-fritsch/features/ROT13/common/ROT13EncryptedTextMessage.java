package common;

public class ROT13EncryptedTextMessage extends EncriptedTextMessage implements ITextMessage {

	private static final long serialVersionUID = 1L;

	public ROT13EncryptedTextMessage(ITextMessage message) {
		_message = message;
	}
	
	@Override
	protected String encrypt(String message) {
		return Encrypter.encryptROT13(message);
	}
}