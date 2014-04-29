package common.message;

import common.crypto.ICryptoAlgorithm;

/**
 * serializable message that can be send over the sockets between client and
 * server.
 */
public class TextMessage extends AbstractMessage {

	@Override
	public void encode(ICryptoAlgorithm cryptoAlgorithm) {
		this.content = (String) cryptoAlgorithm.encode(this.content);
	}

	@Override
	public void decode(ICryptoAlgorithm cryptoAlgorithm) {
		this.content = (String) cryptoAlgorithm.decode(this.content);
	}

}
