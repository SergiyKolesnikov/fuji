package common; 

import java.io.Serializable; 

public  interface  CryptoProvider  extends Serializable {
	
	/**
	 * @param cipherText Base64 encoded if not a String
	 * @return plain text
	 */
	public String decrypt(String cipherText);

	
	public String encrypt(String plainText);


}
