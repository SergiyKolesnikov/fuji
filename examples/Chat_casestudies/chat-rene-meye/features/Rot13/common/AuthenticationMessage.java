package common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * TODO description
 */
public class AuthenticationMessage {
	public AuthenticationMessage(String username, String password) {
		this.passwordHash = rot13(password);
	}
	
	private static String rot13(String plaintext) {
		String hashvalue="";
        for (int i = 0; i < plaintext.length(); i++) {
            char c = plaintext.charAt(i);
            if       (c >= 'a' && c <= 'm') c += 13;
            else if  (c >= 'A' && c <= 'M') c += 13;
            else if  (c >= 'n' && c <= 'z') c -= 13;
            else if  (c >= 'N' && c <= 'Z') c -= 13;
            
            hashvalue += c;
        }
        return hashvalue;
	}
}