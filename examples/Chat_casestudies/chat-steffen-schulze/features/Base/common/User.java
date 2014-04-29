package common;

public class User {
	private String username;
	private String password;
	private String color;
	
	public User(String username, String password, String color)
	{
		super();
		this.username = username;
		this.password = password;
		this.color = color;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public String getColor() {
		return color;
	}
}
