
public class CipherROT13 implements Cipher {

	@Override
	public String encode(String textToEncode) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < textToEncode.length(); i++) {
        	char c= (char)((textToEncode.charAt(i) + 13) % 0xFFFF);
        	b.append(c);
        }
        return b.toString();
	}

	@Override
	public String decode(String cipher) {
		 StringBuilder b = new StringBuilder();
	        for (int i = 0; i < cipher.length(); i++) {
	        	char n= (char)((cipher.charAt(i) + 0xFFFF- 13) % 0xFFFF);
	        	b.append(n);
	        }
	        return b.toString();
	}

}
