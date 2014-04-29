package common; 

import java.io.Serializable; 

/**
 * TODO description
 */
public   class  TextMessage  implements Serializable {
	

//	private static final long serialVersionUID = -9161595018411902079L;
	private String content;

	

	public TextMessage(String content) {
		super();
		this.content = content;
	}

	

	public String getContent() {
		return content;
	}

	
	public String  chatUser;

	
	public String chatToUser = "all";

	
	public boolean whisper = false;


}
