package common; 

public  class  XORCryptoProvider  implements CryptoProvider {
	
	private String key;

	
	
	public XORCryptoProvider(String ikey) {
		key = ikey;
	}

	

	public String encrypt(String plainText) {
		byte[] keyBytes = key.getBytes();
		while(plainText.getBytes().length % keyBytes.length != 0) {
			plainText += " ";
		}
		return new String(xor(plainText.getBytes()));
	}

	

	public String decrypt(String cipherText) {
		return new String(xor(cipherText.getBytes()));
	}

	

	
	private byte[] xor(byte[] in) {
		byte[] out = new byte[in.length];
		byte[] keyBytes = key.getBytes();
		for (int i = 0; i < in.length / keyBytes.length; i++) {
			for (int j = 0; j < keyBytes.length; j++) {
				out[i * keyBytes.length + j] = (byte) (in[i * keyBytes.length + j] ^ keyBytes[j]);
			}
		}
		return out;
	}


}
