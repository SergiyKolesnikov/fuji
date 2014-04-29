import java.awt.Color;

public class TextMessageColored extends TextMessage{

	private static final long serialVersionUID = -4818713858611172437L;
	private Color color;
	
	public TextMessageColored(String content, Color color) {
		super(content);
		this.color = color;
	}
	
	public TextMessageColored(String content) {
		super(content);
		this.color = Config.TEXTCOLOR_CLIENT_DEFAULT;
	}
	
	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
