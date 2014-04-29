package common;

/**
 * Decorator for ITextMessage objects - adds color tags
 * 
 * @author Mikka
 */
public class ColorTextMessage implements ITextMessage {

	private static final long serialVersionUID = 1L;
	private String _color;	
	private ITextMessage _message;
	
	public ColorTextMessage(ITextMessage message, String color) {
		_message = message;
		setColor(color);
		// TODO Auto-generated constructor stub
	}

	public void setColor(String color) {
		_color = color;
	}
	
	private String getColor() {
		return _color;
	}
	
	@Override
	public String toString() {
		return String.format("°%s - %s", getAuthor(), getContent());
	}
	
	public String getContent() {
		return String.format("°%s°%s°!%s°", getColor(), _message.getContent(), getColor());
	}
	
	public String getAuthor() {
		return _message.getAuthor();
	}
}