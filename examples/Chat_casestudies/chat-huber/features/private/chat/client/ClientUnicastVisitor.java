package chat.client; 

import chat.AMessageVisitor;
import chat.IMessageVisitor;
import chat.messages.AMessage;
import chat.messages.ColoredTextMessage;
import chat.messages.DefaultTextMessage;
import chat.messages.LoginMessage;
import chat.messages.PrivateMessage;
import chat.messages.StatusMessage;

public   class  ClientUnicastVisitor  extends AMessageVisitor {
	public void visit(PrivateMessage msg) {
		send(msg);
	}
}
