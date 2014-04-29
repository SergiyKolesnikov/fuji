package server;

import common.EncryptROT13;
import common.EncryptXOR;

public class Client 
{
	protected Boolean handleMessage( String aMsg )
	{		
		String s = aMsg;
		if( s.startsWith("MSG ") ) s = s.substring(4);
		else return original(s);
		
		if( s.startsWith("XOR ") )
		{
			s = s.substring(4);
			s = EncryptXOR.decrypt(s);
		}
		else if( s.startsWith("ROT13 ") )
		{
			s = s.substring(6);
			s = EncryptROT13.decrypt(s);
		}
		
		return original("MSG " + s);
	}
}