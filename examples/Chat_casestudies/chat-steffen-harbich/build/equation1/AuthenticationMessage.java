import java.io.Serializable; 

/**
 * A message that request authorization of a client to a server.
 */
public  class  AuthenticationMessage  implements Message, Serializable {
	

	private static final long serialVersionUID = 7867331750470377343L;

	
	
	private String userName;

	
	private String passPhrase;

	
	
	public AuthenticationMessage(String userName, String passPhrase) {
		this.userName = userName;
		this.passPhrase = passPhrase;
	}

	

	public String getUserName() {
		return userName;
	}

	
	
	public String getPassPhrase() {
		return passPhrase;
	}


}
