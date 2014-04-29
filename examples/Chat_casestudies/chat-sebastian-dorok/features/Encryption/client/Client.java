package client;

import java.util.Random;

import common.crypto.CryptoFactory;
import common.crypto.CryptoFactory.CRYPTO_ALGORITHMS;
import common.message.CryptoMessage;

/**
 * simple chat client
 */
public class Client implements Runnable {

	// at the beginning there is no encryption is enabled
	private CryptoFactory.CRYPTO_ALGORITHMS cryptoAlgorithm = CRYPTO_ALGORITHMS.NO_CRYPT;

	/**
	 * decides what to do with incoming messages
	 * 
	 * @param msg
	 *            the message (Object) received from the sockets
	 */
	protected void handleIncomingMessage(Object msg) {
		((AbstractMessage) msg).decode(CryptoFactory
				.getCryptoModule(this.cryptoAlgorithm));

		if (this.cryptoAlgorithm.equals(CRYPTO_ALGORITHMS.NO_CRYPT)) {
			// only exchange crypto info when NO_CRYPT selected
			if (msg instanceof StatusMessage) {
				StatusMessage m = (StatusMessage) msg;
				if (m.getStatus().equals(StatusMessage.STATUS.CONNECT_SUCC)) {
					// randomly choose one of the supported algorithms
					// supported encryption modules
					CRYPTO_ALGORITHMS[] supportedAlgorithms = {
							CRYPTO_ALGORITHMS.ROT13, CRYPTO_ALGORITHMS.SWAP };
					Random rand = new Random();
					int idx = rand.nextInt(supportedAlgorithms.length);
					this.cryptoAlgorithm = supportedAlgorithms[idx];
					send(new CryptoMessage(supportedAlgorithms,
							this.cryptoAlgorithm));
				}
				// original behavior mustn't be executed
				return;
			}
		}

		original(msg);
	}

	public void send(AbstractMessage msg) {
		msg.encode(CryptoFactory.getCryptoModule(this.cryptoAlgorithm));

		original(msg);
	}
}
