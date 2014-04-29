package chat.client;

import chat.AMessageVisitor;
import chat.IMessageVisitor;
import chat.messages.AMessage;
import chat.messages.ColoredTextMessage;
import chat.messages.DefaultTextMessage;
import chat.messages.LoginMessage;
import chat.messages.StatusMessage;

public class ClientUnicastVisitor extends AMessageVisitor {
	@Override
	public void visit(ColoredTextMessage message) {
		send(message);
	}
}
