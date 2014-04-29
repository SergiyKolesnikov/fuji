package server;

import common.crypto.CryptoFactory;
import common.crypto.CryptoFactory.CRYPTO_ALGORITHMS;
import common.message.StatusMessage;
import common.message.AuthMessage;
import common.message.CryptoMessage;
import common.message.AbstractMessage;
import common.message.StatusMessage.STATUS;
import common.message.TextMessage;

/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
public class Connection extends Thread {
	// by default there is no encryption enabled
	private CryptoFactory.CRYPTO_ALGORITHMS cryptoAlgorithm = CRYPTO_ALGORITHMS.NO_CRYPT;

	/**
	 * decides what to do with incoming messages
	 * 
	 * @param name
	 *            name of the client
	 * @param msg
	 *            received message
	 */
	private void handleIncomingMessage(Object msg) {
		((AbstractMessage) msg).decode(CryptoFactory
				.getCryptoModule(this.cryptoAlgorithm));

		if (msg instanceof CryptoMessage) {
			this.cryptoAlgorithm = ((CryptoMessage) msg).getChosenAlgorithm();
			if (this.cryptoAlgorithm != null) {
				send(new StatusMessage("Selected crypto method: "
						+ this.cryptoAlgorithm + ".", STATUS.CONNECT_SUCC));
			} else {
				send(new StatusMessage(
						"No crypto method selected but is needed.",
						STATUS.CONNECT_FAIL));
				return;
			}
		}

		original(msg);
	}

	/**
	 * sends a message to the client
	 * 
	 */
	public void send(AbstractMessage msg) {
		msg.encode(CryptoFactory.getCryptoModule(this.cryptoAlgorithm));
		original(msg);
	}
}
