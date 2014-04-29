package common.message;

import common.crypto.ICryptoAlgorithm;

public class AuthMessage extends AbstractMessage {

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
