package chat.server.visitors;

import chat.AMessageVisitor;
import chat.IMessageVisitor;
import chat.messages.ColoredTextMessage;
import chat.messages.PrivateMessage;
import chat.messages.LoginMessage;
import chat.messages.StatusMessage;

public class ServerUnicastVisitor extends AMessageVisitor {

	@Override
	public void visit(PrivateMessage message) {
		message.getClient().unicast(message);
		forward(message);
	}

}
