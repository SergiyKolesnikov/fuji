package chat.server.visitors;

import java.util.HashMap;

import chat.AMessageVisitor;
import chat.IMessageVisitor;
import chat.messages.AMessage;
import chat.messages.ColoredTextMessage;
import chat.messages.DefaultTextMessage;
import chat.messages.LoginMessage;
import chat.messages.StatusMessage;
import chat.messages.StatusMessage.Status;

public class AuthenticationVisitor extends AMessageVisitor {
	@Override
	public void visit(ColoredTextMessage message) {
		filter(message);
	}
}
