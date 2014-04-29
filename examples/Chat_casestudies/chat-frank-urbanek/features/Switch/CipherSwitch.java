
/**
 * possible interpretation of "Verschlüsselung ... durch
Vertauschen der ersten beiden Buchstaben" :D
 * @author Frank
 *
 */

public class CipherSwitch implements Cipher {

	@Override
	public String encode(String textToEncode) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < textToEncode.length(); i +=2) {
        	if ((i+1) < textToEncode.length()){
            	b.append(textToEncode.charAt(i+1));
            	b.append(textToEncode.charAt(i));
        	}else if (i < textToEncode.length()){
        		b.append(textToEncode.charAt(i));
        	}
        	
        }
        return b.toString();
	}

	@Override
	public String decode(String cipher) {
		return encode(cipher);
	}

}
