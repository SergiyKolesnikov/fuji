package common.crypto; 

public  class  SWAP  implements ICryptoAlgorithm {
	

	@Override
	public Object encode(Object o) {
		if (o instanceof String) {
			String str = (String) o;
			StringBuilder enc = new StringBuilder();
			// extract words
			String[] words = str.split(" ");
			// swap first two letters of each word
			for (int i = 0; i < words.length; i++) {
				if (i > 0) {
					enc.append(" ");
				}
				enc.append(swapLetters(words[i]));
			}
			return enc.toString();
		}
		return null;
	}

	

	@Override
	public Object decode(Object o) {
		return encode(o);
	}

	

	private String swapLetters(String word) {
		StringBuilder ret = new StringBuilder();
		if (word.length() >= 2) {
			ret.append(word.charAt(1));
			ret.append(word.charAt(0));
			ret.append(word.substring(2));
		} else {
			ret.append(word);
		}
		return ret.toString();
	}


}
