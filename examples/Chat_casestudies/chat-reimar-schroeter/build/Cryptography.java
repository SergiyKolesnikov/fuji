




/**
 * 
 * class to encrypt/decrypt text
 * 
 */
abstract class Cryptography$$Security {
	protected Cryptography rek;
	
	/**
	 * create one Cryptography subclass of the given kind
	 * 
	 * @param kind of the subclass to create
	 * @return
	 */
	public static Cryptography create(){
		return null;
	}
	
	/**
	 * encrypt the given string and return the result
	 * 
	 * @param msg text to encrypt
	 * @return encrypted text
	 */
	public abstract String encrypt(String msg);

	/**
	 * decrypt the given string and return the result
	 * 
	 * @param msg text to decrypt
	 * @return decrypted text
	 */
	public abstract String decrypt(String msg);
}



abstract class Cryptography$$Reverse extends  Cryptography$$Security  {
	public static Cryptography create(){
		Cryptography cr = Cryptography$$Security.create();
		if(cr == null){
			return new Reverse_Cryption();
		}
		else{
			return new Reverse_Cryption(cr);
		}
	}
}



public abstract class Cryptography extends  Cryptography$$Reverse  {
	public static Cryptography create(){
		Cryptography cr = Cryptography$$Reverse.create();

		if(cr == null){
			return new Rot_Cryption();
		}
		else{
			return new Rot_Cryption(cr);
		}
		
	}
}