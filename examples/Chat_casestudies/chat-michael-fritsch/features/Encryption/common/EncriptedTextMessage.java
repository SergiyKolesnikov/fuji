package common;

/**
 * Decorator for ITextMessage objects - encrypts the content
 * 
 * @author Mikka
 */
public abstract class EncriptedTextMessage implements ITextMessage {

	private static final long serialVersionUID = 1L;
	protected ITextMessage _message;
		
	@Override
	public String toString() {
		return String.format("°%s - %s", getAuthor(), getContent());
	}
	
	public String getAuthor() {
		return _message.getAuthor();
	}

	public String getContent() {
		return encrypt(_message.getContent());
	}
	
	protected abstract String encrypt(String message);
}
