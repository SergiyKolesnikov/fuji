package common.message;

import common.message.AbstractMessage;

public class AuthMessage extends AbstractMessage {

	private static final long serialVersionUID = 7721579170680941725L;

	private String user;
	private String password;

	public AuthMessage(String user, String password) {
		this.user = user;
		this.password = password;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

}
