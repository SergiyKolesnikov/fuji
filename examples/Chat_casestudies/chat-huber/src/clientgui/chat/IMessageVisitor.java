package chat; 

import chat.messages.ColoredTextMessage; 
import chat.messages.DefaultTextMessage; 
import chat.messages.LoginMessage; 
import chat.messages.StatusMessage; 

import chat.messages.PrivateMessage; 

public   interface  IMessageVisitor {
	
	public void visit(LoginMessage message);

	

	public void visit(DefaultTextMessage message);

	

	public void visit(StatusMessage message);

	

	public void setNextVisitor(IMessageVisitor next);

	
	public void visit(ColoredTextMessage message);

	
	public void visit(PrivateMessage message);


}
