package network.server; 

import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 

public  class  Hash {
	

	
	public static byte[] sha1(byte[] data){
		MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(sha != null){
			sha.update(data);
		    return sha.digest();
		}
	    return null;
	}


}
