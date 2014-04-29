package crypto; 

public  class  AlphaBetaCipher  extends AbstractCiphering {
	

	@Override
	public String encode(String phrase) {
		return alphaBetaExchange(phrase);
	}

	

	@Override
	public String decode(String code) {
		return alphaBetaExchange(code);
	}

	

	private String alphaBetaExchange(String text) {
		StringBuffer strBuf = new StringBuffer();
		char[] characters = text.toCharArray();
		boolean wordStart = true;
		char cSwap;
		for (int i = 0; i < characters.length - 1; i++) {
			if (Character.isWhitespace(characters[i])) {
				wordStart = true;
			} else if (wordStart && !Character.isWhitespace(characters[i + 1])) {
				cSwap = characters[i + 1];
				characters[i + 1] = characters[i];
				characters[i] = cSwap;
				wordStart = false;
			}
			strBuf.append(characters[i]);
		}
		strBuf.append(characters[characters.length - 1]);
		return strBuf.toString();
	}

	

	@Override
	public String getCipheringName() {
		return "AlphaBetaCipher";
	}


}
