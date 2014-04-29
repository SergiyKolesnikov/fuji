package chat;

import chat.messages.AMessage;
import chat.messages.ColoredTextMessage;

public abstract class AMessageVisitor implements IMessageVisitor {
	@Override
	public void visit(ColoredTextMessage message) {
		forward(message);
	}
}
