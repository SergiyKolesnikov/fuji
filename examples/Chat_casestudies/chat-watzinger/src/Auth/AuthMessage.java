public  class  AuthMessage  extends Message {
	

	private static final long serialVersionUID = -1269523461323773415L;

	
	private String password;

	

	public AuthMessage(String source, String password) {
		super(source);
		this.password = password;
	}

	

	public void setPassword(String password) {
		this.password = password;
	}

	

	public String getPassword() {
		return password;
	}

	

	public String toString() {
		return "AuthMessage || Source: " + getSource() + " | Password: "
				+ getPassword();
	}


}
