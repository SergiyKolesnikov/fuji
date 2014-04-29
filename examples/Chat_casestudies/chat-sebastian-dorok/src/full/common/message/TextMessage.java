package common.message; 

import common.message.AbstractMessage; 

import common.crypto.ICryptoAlgorithm; 

/**
 * serializable message that can be send over the sockets between client and
 * server.
 */
public   class  TextMessage  extends AbstractMessage {
	

	private static final long serialVersionUID = -9161595018411902079L;

	
	private String content;

	
	private String from;

	

	public TextMessage  (String content) {
		this.content = content;
	
		// constructors won't be replaced but extended *narf*
		this.textColor = TextColor.NONE;
	}

	

	public String getFrom() {
		return this.from;
	}

	

	public void setFrom(String from) {
		this.from = from;
	}

	

	public String getContent() {
		return this.content;
	}

	

	private TextColor textColor;

	

	public TextMessage(String content, TextColor textColor) {
		this.content = content;
		this.textColor = textColor;
	}

	

	public TextColor getTextColor() {
		return this.textColor;
	}

	

	@Override
	public void encode(ICryptoAlgorithm cryptoAlgorithm) {
		this.content = (String) cryptoAlgorithm.encode(this.content);
	}

	

	@Override
	public void decode(ICryptoAlgorithm cryptoAlgorithm) {
		this.content = (String) cryptoAlgorithm.decode(this.content);
	}


}
