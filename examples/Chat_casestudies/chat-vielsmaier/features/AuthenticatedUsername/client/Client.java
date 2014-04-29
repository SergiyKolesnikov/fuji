package client; 

import common.AuthFailedMessage; 
import common.AuthMessage; 
import common.AuthOKMessage; 

public  class  Client {
	private String password = "";
	private String username = "";
	private boolean authenticated = false;
	
	private void handleMessage(Message o) {
		original(o);
		if(o.getClass().equals(AuthFailedMessage.class)) {
			for (Iterator i = chatListeners.iterator(); i.hasNext();) {
				((ChatListener) i.next()).requestLogin();
			}
		} else if(o.getClass().equals(AuthOKMessage.class)) {
			authenticated = true;
		}
	}	

	public void login(String iuserName, String ipassword) {
		username = iuserName;
		password = ipassword;
		send(new AuthMessage(username, password));
	}

	public boolean isAuthenticated() {
		return authenticated;
	}
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void connect() {
		original();
		handleMessage(new AuthFailedMessage());		
	}
}
