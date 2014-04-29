package common;

import java.io.Serializable;

/**
 * serializable message that can be send over the sockets between client and
 * server.
 */
public class TextMessage implements Serializable {

	private String color;

	public TextMessage(String content) {
		this.color = null;
	}


	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
