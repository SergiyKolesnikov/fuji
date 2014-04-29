package common; 

import java.io.Serializable; 

import client.Client; 

import server.Server; 

import java.math.BigInteger; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 

/**
 * TODO description
 */
public   class  AuthenticationMessage  implements Serializable {
	

	private static final long serialVersionUID = -9161550184923902079L;

	
	private String username;

	
	private String passwordHash;

	
	public AuthenticationMessage  (String username, String password) {
		super();
		
		this.username = username;
		this.passwordHash = password;
	
		this.passwordHash = md5(password);
	}

	

	public String getPasswordHash() {
		return passwordHash;
	}

	
	
	public String getUsername(){
		return username;
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
