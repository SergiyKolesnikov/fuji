package chat;

import chat.messages.ColoredTextMessage;
import chat.messages.DefaultTextMessage;
import chat.messages.LoginMessage;
import chat.messages.StatusMessage;

public interface IMessageVisitor {
	public void visit(ColoredTextMessage message);
}
