public class SecuredMessage extends Message{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String password;
	private Message msg;
	
	
	public SecuredMessage(Message msg, String password) {
		super();
		this.password = password;
		this.msg = msg;
	}

	public String getPassword() {
		return password;
	}
	
	public Message getMessage() {
		return msg;
	}

	
}
