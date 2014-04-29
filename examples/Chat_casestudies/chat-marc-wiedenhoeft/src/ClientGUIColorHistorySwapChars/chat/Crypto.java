package chat; 

/**
 * This class provides methods to encrypt or decrypt a String in 2 different ways:<br>
 * - Swap the first 2 letters<br>
 * - ROT13 En/Decryption<br>
 * 
 * @author Marc Wiedenhöft
 * @version 1.0
 */
public  class  Crypto {
		
	/**
	 * Crypto a text message.
	 * This method encrypts normal text and decrypts an encrypted message.
	 */
	public static String doSwapCrypto(String msg)
	{
		/**
		 * The text need to be at least 2 chars long for the change crypto.
		 * It swaps the first two letters and returns the result. 
		 */
		String first = String.valueOf(msg.charAt(0));
		String second = String.valueOf(msg.charAt(1));
			
		msg = second + first + msg.substring(2);
		
		return msg; 
	}


}
