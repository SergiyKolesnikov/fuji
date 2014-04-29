public abstract class View implements Observer {
	private String username;
	
	public void setUsername(String username) {
		this.username = username;
		client.send(new UserNameMessage(username));
	}
	
	public String getUsername() {
		return username;
	}
}
