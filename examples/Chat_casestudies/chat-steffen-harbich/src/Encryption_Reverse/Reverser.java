/**
 * A simple reversing of the text (as reading the characters from last to first) as
 * encryption algorithm.
 */
public class Reverser implements EncryptionAlgorithm {

	public String decrypt(String content) {
		return encrypt(content);
	}

	public String encrypt(String content) {
		String result = "";
		
		for (int i = content.length() - 1; i >= 0; i--)
			result +=  content.charAt(i);

		return result;
	}
	
	@Override
	public String toString() {
		return "Reverser";
	}

}
