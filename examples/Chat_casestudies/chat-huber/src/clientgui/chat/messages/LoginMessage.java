package chat.messages; 

import chat.IMessageVisitor; 

public  class  LoginMessage  extends AMessage {
	

	private String password;

	
	private String username;

	

	public LoginMessage(String password, String username) {
		super();
		this.password = password;
		this.username = username;
	}

	

	public String getPassword() {
		return password;
	}

	

	public void setPassword(String password) {
		this.password = password;
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
