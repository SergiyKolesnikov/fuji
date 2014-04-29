package chat.client;  

import chat.AMessageVisitor; 
import chat.IMessageVisitor; 
import chat.messages.AMessage; 
import chat.messages.ColoredTextMessage; 
import chat.messages.DefaultTextMessage; 
import chat.messages.LoginMessage; 
import chat.messages.StatusMessage; 
import chat.messages.PrivateMessage; 

public     class   ClientUnicastVisitor   extends AMessageVisitor {
	

	public ClientUnicastVisitor(IMessageVisitor next) {
		super(next);
	}

	

	@Override
	public void visit(LoginMessage message) {
		send(message);
	}

	

	@Override
	public void visit(DefaultTextMessage message) {
		send(message);
	}

	


	public void visit(StatusMessage message) {
		send(message);
	}

	

	private void send(AMessage message) {
		message.getClient().unicast(message);
		forward(message);
	}

	
	@Override
	public void visit(ColoredTextMessage message) {
		send(message);
	}

	
	public void visit(PrivateMessage msg) {
		send(msg);
	}


}
