

public  class Encrypter{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String key;
	
	public Encrypter(String key) {
		
		if (key.equals("rot13") || key.equals("scramble2")) {
			this.key = key;
		}	
	}
	/**
	 * Message encryption: builds encrypted Message out of any Message
	 * @param message
	 * @return Encrypted Message
	 */
	public EncryptedMessage encrypt(Message message) {
		
		String encMessage = "";
		String encToken;
		EncryptedMessage outMessage = new EncryptedMessage();
		
		if(message instanceof TextMessage) {
			String line = ((TextMessage)message).getContent();
			String[] tokens = line.split("\\s");
			for(int j = 0; j < tokens.length; j++) {
				String t = tokens[j];			
				//rot13 algorithm
				if (((Encrypter) this).key.equals("rot13")){
					byte[] token = t.getBytes();
					byte offset = 65;
					byte rotation = 13;
					for (int i = 0; i < token.length ; i++) {
						
						if (token[i] >= 65 && token[i] <= 109) {
							token[i] = (byte) ( token[i] + rotation );
						} else if (token[i] > 109 && token[i] <= 122) {
							token[i] = (byte) ((13 - (122 - token[i])) + offset);
						} else {
							token[i] = token[i];
						}
						
					}
					encToken = new String(token);
				//scramble 2 algorithm	
				} else if (((Encrypter) this).key.equals("scramble2")) {
					
					char[] chararray = t.toCharArray();
					char c1 = chararray[0];
					chararray[0] = chararray[1];
					chararray[1] = c1;
					
					encToken = new String(chararray);
					
				} else {
					encToken = null;
				}
				
				encMessage = encMessage + " " + encToken;
			}
		}	
		outMessage.setEncryptedMessage(encMessage);
		return outMessage;
	}
	
	/**
	 * Message decryption: returns decrypted Content of a Encrypted Message
	 * @param message
	 * @return String
	 */
	public String decrypt(EncryptedMessage message) {
		
		String decToken;
		String decString = "";
		
		String encMessage = message.getEncryptedMessage();
		
		String[] tokens = encMessage.split("\\s");
		for(int j = 0; j < tokens.length; j++) {
				String t = tokens[j];			
			
			//decrypt rot13 algorithm
			if (((Encrypter) this).key.equals("rot13")) {
			
				byte[] token = t.getBytes();
				byte rotation = 13;
				for (int i = 0; i < token.length ; i++) {
					if (token[i] > 78 && token[i] <= 122) {
						token[i] = (byte) ( token[i] - rotation );
					} else if (token[i] >= 65 && token[i] <= 78) {
						token[i] = (byte) (122 - (13 - (token[i] - 65)));
					} else {
						token[i] = token[i];
					}
				}
				decToken = new String(token);
			
			//decrypt scramble2 algorithm
			} else if (((Encrypter) this).key.equals("scramble2")) {
				char[] chararray = t.toCharArray();
				char c1 = chararray[0];
				chararray[0] = chararray[1];
				chararray[1] = c1;
			
				decToken = new String(chararray);
		
			} else {
				decToken = null;
			}
			decString = decString + " " + decToken;
		}
		return decString;
	}

	public void setKey(String key) {
		
	}
	
	public String getKey(){
		return key;
	}
}