package common;

public class SwappedEncryptedTextMessage extends EncriptedTextMessage implements ITextMessage {

	private static final long serialVersionUID = 1L;
	
	public SwappedEncryptedTextMessage(ITextMessage message) {
		_message = message;
	}
	
	@Override
	protected String encrypt(String message) {
		return Encrypter.encryptSwapFirstCharacters(message);
	}
}