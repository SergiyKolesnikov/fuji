package client;

import common.CryptoProvider;
import common.EncryptedMessage;
import common.Message;
import common.TextMessage;
import common.XORCryptoProvider;

public class Client {
	
	private CryptoProvider cryptoProvider = new XORCryptoProvider("lala");
	
	private void handleMessage(Message o) {
		original(o);
		if (o.getClass().equals(EncryptedMessage.class)) {
			handleMessage(((EncryptedMessage)o).decrypt(cryptoProvider));
		}
	}
	
	public void sendEncrypted(String i) {
		send(new EncryptedMessage(getUsername(), i, cryptoProvider));
	}
	
	public void setCryptoProvider(CryptoProvider c) {
		cryptoProvider = c;
	}
}
