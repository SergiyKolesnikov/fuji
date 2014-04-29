package chat.messages; 

import chat.IMessageVisitor; 

public  class  PrivateMessage  extends DefaultTextMessage {
	

	private String username;

	

	public PrivateMessage(String username, String text) {
		super(text);
		this.username = username;
	}

	

	public String getUsername() {
		return username;
	}

	

	public void setUsername(String username) {
		this.username = username;
	}

	

	@Override
	public void accept(IMessageVisitor visitor) {
		visitor.visit(this);
	}


}
