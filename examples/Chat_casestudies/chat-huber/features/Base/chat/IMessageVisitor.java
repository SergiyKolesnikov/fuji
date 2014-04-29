package chat;

import chat.messages.ColoredTextMessage;
import chat.messages.DefaultTextMessage;
import chat.messages.LoginMessage;
import chat.messages.StatusMessage;

public interface IMessageVisitor {
	public void visit(LoginMessage message);

	public void visit(DefaultTextMessage message);

	public void visit(StatusMessage message);

	public void setNextVisitor(IMessageVisitor next);
}
