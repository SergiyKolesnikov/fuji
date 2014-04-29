package common;

import java.io.Serializable;

/**
 * serializable message that can be send over the sockets between client and
 * server. 
 */
public class TextMessage implements Serializable {

	private static final long serialVersionUID = -9161595018411902079L;
	private String content;
	private String source = null;
	
	public TextMessage(String content, String source) {
		super();
		this.setContent(content);
		this.source = source;
	}
	
	private void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}
	
	public String getSource() {
		return this.source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
}
