package common;

public class ColoredMessage extends TextMessage {
	private String  color;
	
	public ColoredMessage(String from, String text, String icolor) {
		super(from, text);
		color = icolor;
	}
	
	public String getColor() {
		return color;
	}
}