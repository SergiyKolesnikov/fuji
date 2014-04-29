package chat;

import chat.messages.AMessage;
import chat.messages.PrivateMessage;

public abstract class AMessageVisitor implements IMessageVisitor {
	public void visit(PrivateMessage message) {
		forward(message);
	}
}
