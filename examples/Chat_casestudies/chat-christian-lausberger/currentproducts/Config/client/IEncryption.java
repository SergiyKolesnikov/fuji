package client; 


public  interface  IEncryption  extends IMessageExtension {
	
	public String encode(String text);

	

	public String decode(String text);


}
