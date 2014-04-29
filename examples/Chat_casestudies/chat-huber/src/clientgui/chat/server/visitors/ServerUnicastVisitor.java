package chat.server.visitors; 

import chat.AMessageVisitor; 
import chat.IMessageVisitor; 
import chat.messages.ColoredTextMessage; 
import chat.messages.DefaultTextMessage; 
import chat.messages.LoginMessage; 
import chat.messages.StatusMessage; 
import chat.messages.PrivateMessage; 

public   class  ServerUnicastVisitor  extends AMessageVisitor {
	

	public ServerUnicastVisitor(IMessageVisitor next) {
		super(next);
	}

	

	@Override
	public void visit(LoginMessage message) {
		forward(message);
	}

	

	@Override
	public void visit(DefaultTextMessage message) {
		forward(message);
	}

	

	@Override
	public void visit(StatusMessage message) {
		message.getClient().unicast(message);
		forward(message);
	}

	

	@Override
	public void visit(ColoredTextMessage message) {
		forward(message);
	}

	

	@Override
	public void visit(PrivateMessage message) {
		message.getClient().unicast(message);
		forward(message);
	}


}
