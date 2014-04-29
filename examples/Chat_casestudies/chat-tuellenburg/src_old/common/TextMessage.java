package common;


//added: 13.05
//represents standard text message
public class TextMessage extends Message {

	private static final long serialVersionUID = -622438133248705039L;

	private String content;

	public TextMessage(String content) {
		super();
		this.content = content;
	}

	public String getContent() {
		return content;
	}
}
