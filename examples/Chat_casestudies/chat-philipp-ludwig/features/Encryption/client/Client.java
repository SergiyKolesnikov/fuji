package client;

import common.EncryptROT13;
import common.EncryptXOR;

public class Client 
{
	public Boolean write(String aMsg)
	{
		if( aMsg.startsWith("MSG /rot13 ") ) 
		{
			String data = aMsg.substring(11);	
			aMsg = "MSG ROT13 " + EncryptROT13.encrypt(data);
		}
		else if( aMsg.startsWith("MSG /xor ") )
		{
			String data = aMsg.substring(8);
			aMsg = "MSG XOR " + EncryptXOR.encrypt(data);
		}
		return original(aMsg);
	}
	
}