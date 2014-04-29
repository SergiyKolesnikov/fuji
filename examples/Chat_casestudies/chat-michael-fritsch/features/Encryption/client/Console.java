package client;

import client.Client;
import common.*;

public class Console{

	private Encryption _encryption = null;
	private String _encryptionParam = "-setencryption";

	private boolean feat_Encryption_Encryption(Client client, String line) {
		
		if (line.toLowerCase().startsWith(_encryptionParam))
		{
			setEncryption(client, line.substring(_encryptionParam.length() + 1));
			return true;
		}
		return false;
	}
	
	private void setEncryption(Client client, String newEncryption){
		for(Encryption enc : Encryption.values()) {
			if (enc.toString().toLowerCase().equals(newEncryption.toLowerCase()))
			{
				_encryption = enc;
				client.fireAddLine("Encryption method set to " + enc.toString());
				return;
			}
		}
		_encryption = null;
		client.fireAddLine("Encryption method set to no encryption");
	}		
	
	private ITextMessage feat_Encryption_getEncryptedMessage(ITextMessage msg) {
		if (_encryption != null){
			switch (_encryption) {
				case ROT13:
					return feat_ROT13_getROT13EncryptedMessage(msg);			
				case SwapFirstCharacters:
					return feat_Swapped_getSwappedEncryptedMessage(msg);
			}
		}
		return msg;
	}
	
	private ITextMessage feat_ROT13_getROT13EncryptedMessage(ITextMessage msg)	{ return msg; }
	private ITextMessage feat_Swapped_getSwappedEncryptedMessage(ITextMessage msg)	{ return msg; }
}
