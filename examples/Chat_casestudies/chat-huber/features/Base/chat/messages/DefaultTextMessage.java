package chat.messages;

import chat.IMessageVisitor;

public class DefaultTextMessage extends AMessage {
	private String content;

	public DefaultTextMessage(String content) {
		super();
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public void accept(IMessageVisitor visitor) {
		visitor.visit(this);
	}

}
