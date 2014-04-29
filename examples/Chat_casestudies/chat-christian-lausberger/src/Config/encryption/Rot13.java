package encryption; 

public  class  Rot13  extends Encryption {
	

	public String encode(String text) {
		char[] encode = text.toCharArray();
		
		for (int i = 0; i < encode.length; i++) {
			
			if (encode[i] >= 'a' && encode[i] <= 'm')
				encode[i] += 13;
			else if (encode[i] >= 'A' && encode[i] <= 'M')
				encode[i] += 13;
			else if (encode[i] >= 'n' && encode[i] <= 'z')
				encode[i] -= 13;
			else if (encode[i] >= 'N' && encode[i] <= 'Z')
				encode[i] -= 13;
		}
		return String.valueOf(encode);
	}

	
	
	public String decode(String text) {
		 return encode(text);
	}


}
