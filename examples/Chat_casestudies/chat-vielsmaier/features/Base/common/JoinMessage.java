package common;

public class JoinMessage extends Message {
	private static final long serialVersionUID = 4779344795848330155L;
	private String username;
	
	public JoinMessage(String iusername) {
		username = iusername;
	}
	
	public String getUsername() {
		return username;
	}
}
