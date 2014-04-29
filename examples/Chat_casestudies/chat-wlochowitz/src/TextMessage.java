
import java.io.Serializable;
import java.awt.Color;



/** Text-Nachricht, die zwischen dem Client und dem Server hin und her geschickt
 * werden kann.
 */
abstract class TextMessage$$Basis implements Serializable {

	private static final long serialVersionUID = -9161595018411902079L;
	
	/** Versender der Nachricht */
	protected String sender;

	/** Inhalt der Nachricht */
	protected String content;
	
	/** Erzeugt eine neue Text-Nachricht.
	 * @param content der Inhalt der Nachricht
	 */
	public TextMessage$$Basis(String content, String sender) {
		this.content = content;
		this.sender = sender;	
	}	

	/** Liefert den Inhalt der Text-Nachricht.
	 * @return der Inhalt der Text-Nachricht
	 */
	public String getContent() {
		return content;
	}
	
	/** Liefert den Versender der Text-Nachricht.
	 * @return der Versender der Text-Nachricht
	 */
	public String getSender() {
		return sender;
	}	
	
	/** Setzt den Sender der Nachricht.
     *@param sender der Sender der Nachricht
     */
	public void setSender(String sender) {
		((TextMessage) this).sender = sender;
	}	
	
	public void setContent(String content) {
		((TextMessage) this).content = content;	
	}		
	
	/** Liefert die Text-Nachricht mit Versender und Inhalt.
	 * @return die Text-Nachricht
	 */
	public String toString() {
		return sender + " - " + content; 
	}	
}



public class TextMessage extends  TextMessage$$Basis  {
	
	private static final long serialVersionUID = -9161595018411902079L;
	
	private Color color;
	
	public Color getColor() {
		return color;
	}			
	
	public void setColor(Color c) {
		((TextMessage) this).color = c;
	}
      // inherited constructors


	
	/** Erzeugt eine neue Text-Nachricht.
	 * @param content der Inhalt der Nachricht
	 */
	public TextMessage ( String content, String sender ) { super(content, sender); }	
}