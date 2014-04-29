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

public   class  AuthenticationVisitor  extends AMessageVisitor {
	
	public static final String PASSWORD = "password";

	

	private HashMap states = new HashMap();

	

	public AuthenticationVisitor(IMessageVisitor next) {
		super(next);
	}

	

	@Override
	public void visit(DefaultTextMessage message) {
		filter(message);
	}

	

	@Override
	public void visit(LoginMessage message) {
		StatusMessage status;
		if (message.getPassword().equals(PASSWORD)) {
			states.put(message.getClient(), message.getUsername());
			status = new StatusMessage(Status.LOGIN_SUCCESSFULL);
			message.getClient().setAuthenticated(true);
		} else {
			states.remove(message.getClient());
			status = new StatusMessage(Status.LOGIN_FAILED);
			message.getClient().setAuthenticated(false);
		}
		status.setClient(message.getClient());
		forward(status);
		forward(message);
	}

	

	@Override
	public void visit(StatusMessage message) {
		filter(message);
	}

	

	private void filter(AMessage message) {
		if (!states.containsKey(message.getClient())) {
			message.getClient().setAuthenticated(false);
			StatusMessage status = new StatusMessage(Status.NOT_LOGGEDIN);
			status.setClient(message.getClient());
			forward(status);
		} else {
			message.getClient().setAuthenticated(true);
			message.setOrigin(states.get(message.getClient()).toString());
			forward(message);
		}
	}

	
	@Override
	public void visit(ColoredTextMessage message) {
		filter(message);
	}


}
