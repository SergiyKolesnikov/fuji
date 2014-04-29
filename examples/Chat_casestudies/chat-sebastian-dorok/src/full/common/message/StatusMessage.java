package common.message; 

import common.crypto.ICryptoAlgorithm; 

public   class  StatusMessage  extends AbstractMessage {
	

	private static final long serialVersionUID = -475507407462883614L;

	

	public enum  STATUS {
		CONNECT_SUCC ,  CONNECT_FAIL ,  AUTH_SUCC ,  AUTH_FAIL ,  USER_JOINED ,  USER_LEFT ,  SERVER_WARN ,  SERVER_INFO}

	;

	

	private String reason;

	
	private STATUS status;

	

	public StatusMessage(String reason, STATUS status) {
		this.reason = reason;
		this.status = status;
	}

	

	public String getReason() {
		return this.reason;
	}

	

	public STATUS getStatus() {
		return this.status;
	}

	

	@Override
	public void encode(ICryptoAlgorithm cryptoAlgorithm) {
		this.reason = (String) cryptoAlgorithm.encode(this.reason);
	}

	

	@Override
	public void decode(ICryptoAlgorithm cryptoAlgorithm) {
		this.reason = (String) cryptoAlgorithm.decode(this.reason);
	}


}
