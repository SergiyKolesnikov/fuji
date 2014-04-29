package common.message; 

import common.message.AbstractMessage; 

import common.crypto.ICryptoAlgorithm; 

public   class  AuthMessage  extends AbstractMessage {
	

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

	

	@Override
	public void encode(ICryptoAlgorithm cryptoAlgorithm) {
		this.user = (String) cryptoAlgorithm.encode(this.user);
		this.password = (String) cryptoAlgorithm.encode(this.password);
	}

	

	@Override
	public void decode(ICryptoAlgorithm cryptoAlgorithm) {
		this.user = (String) cryptoAlgorithm.decode(this.user);
		this.password = (String) cryptoAlgorithm.decode(this.password);
	}


}
