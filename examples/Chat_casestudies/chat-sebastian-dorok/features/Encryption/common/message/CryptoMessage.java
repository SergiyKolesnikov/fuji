package common.message;

import common.crypto.CryptoFactory.CRYPTO_ALGORITHMS;
import common.crypto.ICryptoAlgorithm;

public class CryptoMessage extends AbstractMessage {

	private static final long serialVersionUID = -3193714603029515553L;

	private CRYPTO_ALGORITHMS[] supportedAlgorithms;
	private CRYPTO_ALGORITHMS chosenAlgorithm;

	public CryptoMessage(CRYPTO_ALGORITHMS[] algorithms,
			CRYPTO_ALGORITHMS algortihm) {
		this.supportedAlgorithms = algorithms;
		this.chosenAlgorithm = algortihm;
	}

	public CRYPTO_ALGORITHMS[] getSupportedAlgorithms() {
		return this.supportedAlgorithms;
	}

	public CRYPTO_ALGORITHMS getChosenAlgorithm() {
		return this.chosenAlgorithm;
	}

	@Override
	public void encode(ICryptoAlgorithm cryptoAlgorithm) {
		// nothing to do here
	}

	@Override
	public void decode(ICryptoAlgorithm cryptoAlgorithm) {
		// nothing to do here
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
