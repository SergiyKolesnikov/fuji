package common; 

/**
 * serializable message that can be send over the sockets between client and
 * server. 
 */
public  class  TextMessage  implements ITextMessage {
	

	private static final long serialVersionUID = -9161595018411902079L;

	
	private String _content;

	
	private String _author;

	

	public TextMessage(String content, String author) {
		_content = content;
		_author = author;
	}

	

	public TextMessage(ITextMessage message) {	
		_content = ((TextMessage)message).getContent();
		_author = ((TextMessage)message).getAuthor();
	}

	
	
	@Override
	public String toString(){
		return getAuthor() + " - " + getContent();
	}

	
	
	public String getContent() {
		return _content;
	}

	
	
	public String getAuthor() {
		return _author;
	}


}
