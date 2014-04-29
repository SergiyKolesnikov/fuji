package common.crypto; 

public  class  ROT13  implements ICryptoAlgorithm {
	

	@Override
	public Object encode(Object o) {
		if (o instanceof String) {
			String str = (String) o;
			String enc = "";
			for (int i = 0; i < str.length(); i++) {
				char c = str.charAt(i);
				if (c >= 'a' && c <= 'm')
					c += 13;
				else if (c >= 'A' && c <= 'M')
					c += 13;
				else if (c >= 'n' && c <= 'z')
					c -= 13;
				else if (c >= 'N' && c <= 'Z')
					c -= 13;
				enc += c;
			}
			return enc;
		}
		return null;
	}

	

	@Override
	public Object decode(Object o) {
		return encode(o);
	}


}
