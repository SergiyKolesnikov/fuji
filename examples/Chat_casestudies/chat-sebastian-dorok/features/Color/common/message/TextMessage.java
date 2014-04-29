package common.message;

import common.message.AbstractMessage;

/**
 * serializable message that can be send over the sockets between client and
 * server.
 */
public class TextMessage extends AbstractMessage {

	private TextColor textColor;

	public TextMessage(String content) {
		// constructors won't be replaced but extended *narf*
		this.textColor = TextColor.NONE;
	}

	public TextMessage(String content, TextColor textColor) {
		this.content = content;
		this.textColor = textColor;
	}

	public TextColor getTextColor() {
		return this.textColor;
	}

}
