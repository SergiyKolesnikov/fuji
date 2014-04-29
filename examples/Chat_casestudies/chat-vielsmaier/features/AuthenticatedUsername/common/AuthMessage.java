package common;

public class AuthMessage extends Message {
	private static final long serialVersionUID = -8077352873408784590L;
	private String password;
	private String username;
	
	public AuthMessage(String iusername, String ipassword) {
		username = iusername;
		password = ipassword;
	}

	public String getPassword() {
		return password;
	}
	
	public String getUsername() {
		return username;
	}
}
