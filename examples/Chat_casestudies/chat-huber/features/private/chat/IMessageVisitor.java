package chat;

import chat.messages.PrivateMessage;

public interface IMessageVisitor {
	public void visit(PrivateMessage message);
}
