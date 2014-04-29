package swpl.chat.client;
import java.awt.Color;

import swpl.chat.common.TextMessage;



public class ColorTextMessage extends TextMessage {

	private static final long serialVersionUID = 3040444623853535495L;
	
	private Color color;

	public ColorTextMessage(String content, Color color) {
		super(content);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

}