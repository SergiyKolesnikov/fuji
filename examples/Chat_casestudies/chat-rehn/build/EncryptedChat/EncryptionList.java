
package EncryptedChat;

import java.util.LinkedList;



abstract class EncryptionList$$Encryption {
	public LinkedList methods = new LinkedList();
	
	public EncryptionList$$Encryption() {}
}



abstract class EncryptionList$$Rot13 extends  EncryptionList$$Encryption  {
	
	public EncryptionList$$Rot13() { super(); 

		methods.add(new Rot13Encryption()); }

      // inherited constructors


}



public class EncryptionList extends  EncryptionList$$Rot13  {
	
	public EncryptionList() { super(); 

		methods.add(new ReverseEncryption()); }

      // inherited constructors


}