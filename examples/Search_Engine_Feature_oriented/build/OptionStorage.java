package Base;




/**
 * Klasse, die die EinstellungsmÃ¶glichkeiten fÃ¼r die Suche kapselt.
 * 
 * @author Mr. Pink
 */
public class OptionStorage {
	private int maxResults;
	private boolean searchLargest;
	private boolean searchMostRecent;
	
	public static int      SEARCHLARGEST = 1;
	public static int SEARCHLASTMODIFIED = 2;
	public static int       SEARCHNORMAL = 0;
	private int               searchMode = 0;

	/**
	 * Konstruktor.
	 * 
	 * Initialisiert ein Objekt mit den in den Parametern angegebenen
	 * Einstellungswerten.
	 * 
	 * @param maxResults        maximal angezeigte Anzahl Ergebnisse
	 * @param searchLargest     Anzeige der grÃ¶Ãten Dateien?
	 * @param searchMostRecent  Anzeige der zuletzt verÃ¤nderten Dateien?
	 */
	public OptionStorage(int maxResults, boolean searchLargest, boolean searchMostRecent) {
		this.maxResults       = maxResults;
		this.searchLargest    = searchLargest;
		this.searchMostRecent = searchMostRecent;
	}

	/**
	 * Erstellt eine exakte Kopie des Objekts und gibt sie zurÃ¼ck
	 */
	public OptionStorage clone() {
		return new OptionStorage(maxResults, searchLargest, searchMostRecent);
	}

	/**
	 * Gibt die Anzahl der maximalen Dokumente, die angezeigt werden sollen, zurÃ¼ck.
	 * 
	 * @return  siehe oben
	 */
	public int getMaxResults() {
		return maxResults;
	}

	/**
	 * Setzt die Anzahl der maximalen Dokumente, die angezeigt werden sollen, zurÃ¼ck.
	 * 
	 * @param maxResults  siehe oben
	 */
	public void setMaxResults(int maxResults) {
		((OptionStorage) this).maxResults = maxResults;
	}

	/**
	 * Suchmodus ermitteln
	 * 
	 * @return  true, wenn der Suchmodus "Suche grÃ¶Ãte Dokumente" ist, sonst false
	 */
	public boolean isSearchLargest() {
		return (searchMode == SEARCHLARGEST);
	}
	
	/**
	 * Suchmodus ermitteln
	 * 
	 * @return  true, wenn der Suchmodus "Suche neueste Dokumente" ist, sonst false
	 */
	public boolean isSearchMostRecent() {
		return (searchMode == SEARCHLASTMODIFIED);
	}

	/**
	 * Objekt als String formatieren
	 * 
	 * @return  String-ReprÃ¤senation des Objekts
	 */
	public String toString() {
		return "maxResults: " + maxResults + " searchLargest: " + searchLargest + " searchMostRecent: " + searchMostRecent;
	}
	
	/**
	 * Suchmodus setzen
	 * 
	 * @param searchMode  der neue Suchmodus (eine der SEARCH*-Konstanten)
	 */
	public void setSearchMode(int searchMode){
		((OptionStorage) this).searchMode = searchMode;
	}
	
	/**
	 * Suchmodus ermitteln
	 * 
	 * @return  der Suchmodus
	 */
	public int getSearchMode(){
		return searchMode;
	}
}