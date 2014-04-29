package common;

import common.Base64;

/**
 * TODO description
 */
public class EncryptROT13 
{
	public static String encrypt(String aStr)
	{
		StringBuffer buf = new StringBuffer (aStr);

		for ( int i = 0; i < aStr.length(); i++ ) 
		{			
			char c = aStr.charAt(i);
			if       (c >= 'a' && c <= 'm') c += 13;
			else if  (c >= 'A' && c <= 'M') c += 13;
			else if  (c >= 'n' && c <= 'z') c -= 13;
			else if  (c >= 'N' && c <= 'Z') c -= 13;
			buf.setCharAt(i,c); 
		}

		return Base64.encode(buf.toString());
	}

	public static String decrypt(String aStr)
	{		
		String decoded = Base64.decode(aStr);

		return Base64.decode(encrypt(decoded));
	}

}