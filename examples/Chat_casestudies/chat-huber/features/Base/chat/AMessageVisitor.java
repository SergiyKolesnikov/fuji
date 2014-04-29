package chat;

import chat.messages.AMessage;

public abstract class AMessageVisitor implements IMessageVisitor {
	private IMessageVisitor next;

	public AMessageVisitor(IMessageVisitor next) {
		this.next = next;
	}

	public IMessageVisitor getNextVisitor() {
		return next;
	}

	public void setNextVisitor(IMessageVisitor next) {
		this.next = next;
	}
	
	protected void forward(AMessage message) {
		if (getNextVisitor() != null) {
			message.accept(getNextVisitor());
		}
	}

}
