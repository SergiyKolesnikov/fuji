package client; 

import common.Message; 

public  class  Encryption  implements ChatPlugin {
	

	private byte type = 0x04;

	
	private String cipher;

	
	private char key = 0;

	
	
	/**
	 * 
	 * @param cipher  Supported "ROT","XOR"
	 * @param key	Key for encryption
	 */
	public Encryption ( String cipher , char key , Client client){
		this.cipher= cipher;
		this.key 	= key; 
		client.loadPlugin(this);
		System.out.println("Encryptionplugin loaded.");
	}

	
	
	@Override
	public byte getType() {
		return type;
	}

	

	@Override
	public Message process(Message msg) {
		if ((msg.getStatus()&type)==0x00){
			msg =new Message(decrypt(msg.getText()),msg.getStatus());
			
		}
		return msg;
	}

	
	public String encrypt(String str){
		StringBuffer sb =new StringBuffer(str);
		for (int i=0;i<sb.length();i++){
			sb.setCharAt(i,(char) (sb.charAt(i)^key) );
		}
		return sb.toString();
	}

	
	private String decrypt(String str){
		StringBuffer sb = new StringBuffer(str);
		for (int i=0;i<sb.length();i++){
			sb.setCharAt(i,(char) (sb.charAt(i)^key) );
		}
		return sb.toString();
	}


}
