package chat.server.visitors; 

import chat.AMessageVisitor; 
import chat.IMessageVisitor; 
import chat.messages.ColoredTextMessage; 
import chat.messages.DefaultTextMessage; 
import chat.messages.LoginMessage; 
import chat.messages.StatusMessage; 
import chat.server.IServer; 

public   class  BroadcastVisitor  extends AMessageVisitor {
	

	private IServer server  ;

	

	public BroadcastVisitor(IMessageVisitor next, IServer server) {
		super(next);
		this.server = server;
	}

	

	@Override
	public void visit(DefaultTextMessage message) {
		forward(message);
		server.broadcast(message);
	}

	

	@Override
	public void visit(LoginMessage message) {
		forward(message);
	}

	

	@Override
	public void visit(StatusMessage message) {
		forward(message);
	}

	

	@Override
	public void visit(ColoredTextMessage message) {
		forward(message);
		server.broadcast(message);
	}


}
