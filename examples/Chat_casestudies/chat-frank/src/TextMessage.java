
import java.io.Serializable;



/**
 * serializable message that can be send over the sockets between client and
 * server. 
 */
public class TextMessage implements Serializable {

	private static final int _COLORLENGTH = 10; //Color is Black by authen[ ( Black )].
	private static final long serialVersionUID = -9161595018411902079L;
	private String content;

	public TextMessage(String content) {
		super();
		this.content = content;
	}
	
	public String getContentWithoutColor(){
		
		String temp = content ;
		return  temp.substring(0, temp.length()-_COLORLENGTH);
	}

	public String getContent() {
		return content;
	}
}