

public  class RegisterMessage extends Message {
	private String username;
	
	public RegisterMessage(String name) {
		this.username = name;
	}
	
	public void setUsername(String username) {
		((RegisterMessage) this).username = username;
	}
	
	public String getUsername() {
		return ((RegisterMessage) this).username;		
	}
}