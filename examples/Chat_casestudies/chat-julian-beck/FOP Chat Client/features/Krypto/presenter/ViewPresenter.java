package presenter;

import model.Crypto;

public class ViewPresenter extends Observable implements ActionListener, KeyListener, Observer {
	
	private  Crypto crypt = new Crypto();
	
	private String decrypt(String toDecrypt) {
		return crypt.decrypt(toDecrypt);
	}
	
	private String encrypt(String toEncrypt) {
		 return crypt.encrypt(toEncrypt);
	}
}
