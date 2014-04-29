package chat.server.visitors;

import chat.AMessageVisitor;
import chat.IMessageVisitor;
import chat.messages.ColoredTextMessage;
import chat.messages.DefaultTextMessage;
import chat.messages.LoginMessage;
import chat.messages.StatusMessage;

public class ServerUnicastVisitor extends AMessageVisitor {

	@Override
	public void visit(ColoredTextMessage message) {
		forward(message);
	}
}
