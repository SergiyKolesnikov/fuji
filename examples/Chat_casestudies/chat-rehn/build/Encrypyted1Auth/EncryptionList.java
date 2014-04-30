
package Encrypyted1Auth;

import java.util.LinkedList;



abstract class EncryptionList$$Encryption {
	public LinkedList methods = new LinkedList();
	
	public EncryptionList$$Encryption() {}
}



public class EncryptionList extends  EncryptionList$$Encryption  {
	
	public EncryptionList() { super(); 

		methods.add(new Rot13Encryption()); }

      // inherited constructors


}