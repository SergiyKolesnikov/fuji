package common.message;

/**
 * serializable message that can be send over the sockets between client and
 * server.
 */
public class TextMessage extends AbstractMessage {

	private static final long serialVersionUID = -9161595018411902079L;
	private String content;
	private String from;

	public TextMessage(String content) {
		this.content = content;
	}

	public String getFrom() {
		return this.from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getContent() {
		return this.content;
	}
}
