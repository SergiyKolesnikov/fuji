package common;

import common.Base64;

public class EncryptXOR 
{
	static final String key = "keoibm953450vdpsf";  

	public static String encrypt(String aStr)
	{
		StringBuffer buf = new StringBuffer (aStr);

		// Jedes Zeichen wird einzeln behandelt.
		for ( int i = 0, j = 0; i < aStr.length(); i++, j++ ) 
		{
			// Falls str länger als key ist, muss j an dieser Stelle zurückgesetzt werden.			   
			if ( j >= key.length() ) j = 0;

			// Jedes Zeichen von str wird mit einem Zeichen von key verknüpft.
			buf.setCharAt(i, (char)(aStr.charAt(i) ^ key.charAt(j))); 
		}

		return Base64.encode(buf.toString());
	}

	public static String decrypt(String str)
	{
		return Base64.decode(encrypt(Base64.decode(str)));
	}
}