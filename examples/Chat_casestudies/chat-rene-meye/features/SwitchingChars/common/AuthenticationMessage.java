package common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * TODO description
 */
public class AuthenticationMessage {
	public AuthenticationMessage(String username, String password) {
		this.passwordHash = switchingChars(password);
	}
	
	private static String switchingChars(String plaintext){
		if(plaintext.length() > 2){
			String hashvalue = "";
			hashvalue += plaintext.charAt(1);
			hashvalue += plaintext.charAt(0);
			hashvalue += plaintext.substring(2);
			return hashvalue;
		}else{
			return plaintext;
		}
	}
}