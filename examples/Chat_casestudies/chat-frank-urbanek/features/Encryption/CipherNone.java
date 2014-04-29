public class CipherNone implements Cipher {

	@Override
	public String encode(String plain) {
		return plain;
	}

	@Override
	public String decode(String cipher) {
		return cipher;
	}

}
