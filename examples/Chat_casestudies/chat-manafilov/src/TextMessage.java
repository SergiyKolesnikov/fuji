
import java.io.Serializable;



/**
 * serializable message that can be send over the sockets between client and
 * server. 
 */
abstract class TextMessage$$Base implements Serializable {

	private static final long serialVersionUID = -9161595018411902079L;
	protected String content;

	public TextMessage$$Base(String content) {
		super();
		this.content = content;
	}

	public String getContent() {
		return content;
	}
}

public class TextMessage extends  TextMessage$$Base  {
	
	private static String verschlusselung1(String text){
		if(text.length() > 1){
			char[] cArray = text.toCharArray();
			char tmp = cArray[0];
			cArray[0] = cArray[1];
			cArray[1] = tmp;
			return new String(cArray);
		}else{
			return text;
		}
	}

	public TextMessage(String content) { super(content); 

		((TextMessage) this).content = verschlusselung2(verschlusselung1(content)); }

	
	
	private static String verschlusselung2(String text){
		return new StringBuffer(text).reverse().toString();
	}
	
	public String getContent() {
		return verschlusselung1(verschlusselung2(content));
	}
      // inherited constructors



}