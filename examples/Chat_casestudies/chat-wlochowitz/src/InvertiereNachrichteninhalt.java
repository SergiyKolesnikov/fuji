

/** Encrypter fuer das Verschluesseln 
und Entschluesseln von Zeichenketten mittels Vertauschen 
des ersten mit dem letzten Buchstaben. */
public  class InvertiereNachrichteninhalt {
	
	/** 
	 * Liefert eine verschluesselte Zeichenkette, 
	 * indem die uebergebene Zeichenkette invertiert wird. 
	 * @param text ein beliebiger Text
	 * @return die verschluesselte Zeichenkette
	 */
	public String encrypt(String text) {
		return invertiere(text);
	}

	/**
	 * Entschluesselt eine Zeichenkette.
	 * @param text eine beliebige Zeichenkette
	 * @return die entschluesselte Zeichenkette
	 */
	public String decrypt(String text) {
		return invertiere(text);
	}
	
	/** 
	 * Liefert die invertierte Zeichenkette.
	 * @param text eine beliebige Zeichenkette
	 * @return die invertierte Zeichenkette
	 */
	private String invertiere(String text) {
		StringBuffer sb = new StringBuffer(text);
		sb.reverse();
		return sb.toString();
	}
}