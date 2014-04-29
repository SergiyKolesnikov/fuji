package chat; 

import chat.messages.AMessage; 
import chat.messages.ColoredTextMessage; 
import chat.messages.PrivateMessage; 

public abstract   class  AMessageVisitor  implements IMessageVisitor {
	
	private IMessageVisitor next;

	

	public AMessageVisitor(IMessageVisitor next) {
		this.next = next;
	}

	

	public IMessageVisitor getNextVisitor() {
		return next;
	}

	

	public void setNextVisitor(IMessageVisitor next) {
		this.next = next;
	}

	
	
	protected void forward(AMessage message) {
		if (getNextVisitor() != null) {
			message.accept(getNextVisitor());
		}
	}

	
	@Override
	public void visit(ColoredTextMessage message) {
		forward(message);
	}

	
	public void visit(PrivateMessage message) {
		forward(message);
	}


}
