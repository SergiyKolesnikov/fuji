package crypto; 

public  class  Rot13  extends AbstractCiphering {
	

	@Override
	public String encode(String phrase) {
		if(phrase == null)
			return "";
		StringBuffer strBuff = new StringBuffer();
		for(char c: phrase.toCharArray()){
			strBuff.append(rot13(c));
		}
		return strBuff.toString();
	}

	

	@Override
	public String decode(String code) {
		if(code == null)
			return "";
		StringBuffer strBuff = new StringBuffer();
		for(char c: code.toCharArray()){
			strBuff.append(rot13(c));
		}
		return strBuff.toString();
	}

	
	
	private char rot13(char c) {
		if (c >= 'a' && c <= 'm')
			c += 13;
		else if (c >= 'A' && c <= 'M')
			c += 13;
		else if (c >= 'n' && c <= 'z')
			c -= 13;
		else if (c >= 'N' && c <= 'Z')
			c -= 13;
		return c;
	}

	

	@Override
	public String getCipheringName() {
		return "Rot13";
	}


}
