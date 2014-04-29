package client; 

import java.util.Iterator;

import common.SetUsernameMessage;

public  class  Client {
	private String username = "User";
	
	private void handleMessage(Message o) {
		original(o);
		if(o.getClass().equals(SetUsernameMessage.class)) {
			for (Iterator i = chatListeners.iterator(); i.hasNext();) {
				((ChatListener) i.next()).handleUsernameChange(((SetUsernameMessage)o).getOldName(), ((SetUsernameMessage)o).getNewName());
			}
		}
	}	
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
		send(new SetUsernameMessage("", username));
	}
}
