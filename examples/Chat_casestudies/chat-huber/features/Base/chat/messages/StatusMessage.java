package chat.messages;

import chat.IMessageVisitor;

public class StatusMessage extends DefaultTextMessage {
	
	public StatusMessage(Status status) {
		super("");
		setStatus(status);
	}
	
	public Status getStatus() {
		return Status.valueOf(super.getContent());
	}
	
	public void setStatus(Status status) {
		super.setContent(status.toString());
	}
	
	
	@Override
	public String getContent() {
		return super.getContent();
	}

	@Override
	public void setContent(String content) {
		super.setContent(content);
	}

	@Override
	public void accept(IMessageVisitor visitor) {
		visitor.visit(this);
	}
	
	public enum Status {
		NOOP, LOGIN_SUCCESSFULL, LOGIN_FAILED, NOT_LOGGEDIN, SPAM
	}
}
