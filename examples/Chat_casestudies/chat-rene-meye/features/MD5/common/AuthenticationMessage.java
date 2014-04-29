package common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * TODO description
 */
public class AuthenticationMessage {
	public AuthenticationMessage(String username, String password) {
		this.passwordHash = md5(password);
	}
	
	private static String md5(String plaintext) {
		try {
			MessageDigest m;
			m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(plaintext.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			String hashtext = bigInt.toString(16);
			// Now we need to zero pad it if you actually want the full 32 chars.
			while(hashtext.length() < 32 ){
			  hashtext = "0"+hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
}