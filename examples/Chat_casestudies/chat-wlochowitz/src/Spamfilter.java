
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



/** Klasse Spamfilter zum Pruefen auf Spam-Inhalte. */
public class Spamfilter {
	
	/** Ueberprueft, ob im Inhalt einer eingegegangen Text-Nachricht Woerter 
	 * aus einer Sperrliste enthalten sind und liefert true, falls dem so ist,
	 * ansonsten wird die false geliefert.
	 * @param msg eine beliebige Text-Nachricht
	 * @return true, falls die Text-Nachricht Woerter aus der Sperrliste enthaelt,
	 * ansonsten false.
	 */
	public static boolean hasSpamHint(TextMessage msg) {
		String line = null;
		String content = msg.getContent();
		boolean hasSpamHint = false;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("Sperrliste.txt"));;
			while ((line = br.readLine()) != null) {
				String[] words = line.split(" ");
				for (int i = 0; i < words.length; i++) {
					String text = content.toLowerCase();
					String word = words[i].toLowerCase();
					if (text.indexOf(word) >= 0) {
						hasSpamHint = true;
						break; 
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if(br != null) {
					br.close();
				}
			} catch(IOException e) {
				System.err.println(e.getMessage());
			}			 		
			return hasSpamHint;
		}	
	}
}