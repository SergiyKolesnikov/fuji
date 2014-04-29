import java.awt.Color;

public class ChatMessage extends Message {
	private Color color = Color.BLACK;

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public String toString() {
		return "Chatmessage || Source: " + getSource() + " | Message: "
				+ getMsg() + " | Color: " + getColor();
	}
}
