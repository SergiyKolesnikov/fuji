/**
 * Rot13 encryption algorithm, i.e. each character is rotated by 13.
 */
public  class  Rot13  implements EncryptionAlgorithm {
	

	public String decrypt(String content) {
		return rotate(content, -13);
	}

	

	public String encrypt(String content) {
		return rotate(content, 13);
	}

	
	
	private String rotate(String content, int count) {
		String result = "";
		
		for (int i = 0; i < content.length(); i++) {
			char character = content.charAt(i);
			char newCharacter = (char) ((((int)character) + count) % (((int)Character.MAX_VALUE) + 1));
			result += newCharacter;
		}
		
		return result;
	}

	

	@Override
	public String toString() {
		return "Rot13";
	}


}
