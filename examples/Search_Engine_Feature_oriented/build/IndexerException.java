package Base;




/**
 * Exception die ausgelÃ¶st wird, wenn bei der Indexierung ein Fehler auftritt.
 */
public class IndexerException extends Exception {
	private static final long serialVersionUID = 1L; // Eclipse ruhigstellen, brauchen wir in unserem Environment eigentlich nicht.

	public IndexerException(String message) {
		super(message);
	}
}