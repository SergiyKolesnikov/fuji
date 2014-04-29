

import java.io.Serializable;

public class AuthMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8402477205160158922L;
	private String username;
	private String password;
	
	public AuthMessage(String user, String pw) {
		super();
		this.username = user;
		this.password = pw;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String val) {
		this.username = val;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String val) {
		this.password = val;
	}
	
	public String toString() {
		return "AuthMessage (user: " + username + " pw: " + password+ ")";
	}
}
