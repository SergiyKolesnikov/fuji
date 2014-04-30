package Color;
import java.io.*;
import java.awt.Color;



abstract class TextMessage$$Common implements Serializable {

	private static final long serialVersionUID = -9161595018411902079L;
	protected String content;

	public TextMessage$$Common(String content) {
		super();
		this.content = content;
	}
	public void setContent(String newContent){
		content = newContent;
	}

	public String getContent() {
		return content;
	}
}



public class TextMessage extends  TextMessage$$Common  {
	protected Color textColor = Color.BLACK;
	
	public void setColor(Color newColor){
		textColor = newColor;
	}
	public Color getColor() {
		return textColor;
	}
      // inherited constructors



	public TextMessage ( String content ) { super(content); }
}