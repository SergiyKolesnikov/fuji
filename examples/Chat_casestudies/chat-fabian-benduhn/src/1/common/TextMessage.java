package common; 

import java.io.Serializable; 

/**
 * serializable message that can be send over the sockets between client and
 * server. 
 */
public  class  TextMessage  implements Serializable {
	

	private static final long serialVersionUID = -9161595018411902079L;

	
	protected String content;

	
	private boolean encrypted;

	
	
	public boolean isChangeNick;

	
	public boolean isPrivate;

	
	public String getNewName(){
		
		return content.substring(8);
	}

	
	public String getTargetUser(){
	
		return content.substring(8);
	}

	
	public TextMessage(String content, boolean encrypted) {
		super();
		this.content = content;
		this.encrypted=encrypted;
	}

	

	public String getContent() {
		return content;
	}

	

	public boolean isEncrypted() {
		return encrypted;
	}


}
