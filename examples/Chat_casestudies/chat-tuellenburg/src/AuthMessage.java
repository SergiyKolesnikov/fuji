

public  class AuthMessage extends Message {

	private static final long serialVersionUID = -8077352873408784590L;

	private String username;
	private String password;
	
	//constructor
	public AuthMessage(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername(){
		return ((AuthMessage) this).username;
	}
	
	public String getPassword(){
		return ((AuthMessage) this).password;
	}
}