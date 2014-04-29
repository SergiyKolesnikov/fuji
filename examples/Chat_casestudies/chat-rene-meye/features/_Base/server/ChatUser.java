package server;

public class ChatUser {
	public String username;
	public String passwordHash;
	public String realName;
	
	public ChatUser(String username, String passwordHash, String realname){
		this.username = username;
		this.passwordHash = passwordHash;
		this.realName = realname;
	}
}
