package common; 

public  class  RotationCryptoProvider  implements CryptoProvider {
	
	private static final long serialVersionUID = -3529740390293300573L;

	
	private int rotation;

	
	
	public RotationCryptoProvider(int r) {
		rotation = r;
	}

	

	public String decrypt(String cipherText) {
		String result = "";
		for (int i = 0; i < cipherText.length(); i++) {
			result += (char)(cipherText.charAt(i) - rotation);
		}
		return result;
	}

	

	public String encrypt(String plainText) {
		String result = "";
		for (int i = 0; i < plainText.length(); i++) {
			result += (char)(plainText.charAt(i) + rotation);
		}
		return result;
	}


}
