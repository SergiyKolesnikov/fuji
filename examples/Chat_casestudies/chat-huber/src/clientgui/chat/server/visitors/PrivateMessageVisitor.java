package chat.server.visitors; 

import java.util.HashMap; 

import chat.AMessageVisitor; 
import chat.IMessageVisitor; 
import chat.IClient; 
import chat.messages.AMessage; 
import chat.messages.PrivateMessage; 
import chat.messages.DefaultTextMessage; 
import chat.messages.LoginMessage; 
import chat.messages.StatusMessage; 
import chat.messages.StatusMessage.Status; 

public  class  PrivateMessageVisitor  extends AMessageVisitor {
	
	private HashMap states = new HashMap();

	
	private HashMap inv_states = new HashMap();

	

	public PrivateMessageVisitor(IMessageVisitor next) {
		super(next);
	}

	

	@Override
	public void visit(DefaultTextMessage message) {
		setName(message);
		forward(message);
	}

	

	@Override
	public void visit(PrivateMessage message) {
		if (message.getContent().trim().equals("")) {
			String oldName = (String) inv_states.remove(message.getClient());
			if (oldName != null) {
				states.remove(oldName);
			}
			states.put(message.getUsername(), message.getClient());
			inv_states.put(message.getClient(), message.getUsername());
		} else {
			IClient client = (IClient) states.get(message.getUsername());
			if (client != null) {
				setName(message);
				message.setClient(client);
				forward(message);
			}
		}
	}

	

	@Override
	public void visit(LoginMessage message) {
		forward(message);
	}

	

	@Override
	public void visit(StatusMessage message) {
		forward(message);
	}

	

	private void setName(AMessage message) {
		String name = (String) inv_states.get(message.getClient());
		if (name != null) {
			message.setOrigin(name);
		}
	}


}
