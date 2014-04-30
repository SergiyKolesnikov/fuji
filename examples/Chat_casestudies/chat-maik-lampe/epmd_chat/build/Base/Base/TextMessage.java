package Base;

import java.io.Serializable;



/**
 * serializable message that can be send over the sockets between client and
 * server. 
 */
abstract class TextMessage$$Base implements Serializable {

	private static final long serialVersionUID = -9161595018411902079L;
	protected String content;
	protected String origin;

	public TextMessage$$Base(String content) {
		super();
		this.content = content;
		this.origin="";
	}
	
	public void setOrigin(String origin){
		((TextMessage) this).origin=origin;
	}
	
	public String getOrigin(){
		return ((TextMessage) this).origin;
	}
	
	public void setContent(String content){
		((TextMessage) this).content=content;
	}
	
	public String getContent() {
		return content;
	}
}



abstract class TextMessage$$Flip extends  TextMessage$$Base  {
	protected boolean cryp=false;
      // inherited constructors



	public TextMessage$$Flip ( String content ) { super(content); }
	
	
}



public class TextMessage extends  TextMessage$$Flip  {
	protected String color;
	
	public void setColor(String color){
		((TextMessage) this).color=color;
	}

	public TextMessage(String content) { super(content); 

		((TextMessage) this).content=content;
		((TextMessage) this).color=""; }

	
	public String getColor(){
		return ((TextMessage) this).color;
	}
      // inherited constructors


}