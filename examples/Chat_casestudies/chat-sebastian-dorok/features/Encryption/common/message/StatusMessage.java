package common.message;

import common.crypto.ICryptoAlgorithm;

public class StatusMessage extends AbstractMessage {

	@Override
	public void encode(ICryptoAlgorithm cryptoAlgorithm) {
		this.reason = (String) cryptoAlgorithm.encode(this.reason);
	}

	@Override
	public void decode(ICryptoAlgorithm cryptoAlgorithm) {
		this.reason = (String) cryptoAlgorithm.decode(this.reason);
	}

}
