
public   class  TextMessageImpl  implements TextMessage {
	

	private static final long serialVersionUID = -9161595018411902079L;

	

	private String content;

	
	
	public TextMessageImpl  () {
		this.content = null;
	
		this.color = null;
	}

	

	public String  getContent__wrappee__ColoredConsole  () {
		return content;
	}

	

	public String getContent() {
		return encryptionAlgorithm.decrypt(getContent__wrappee__ColoredConsole());
	}

	
	
	public void  setContent__wrappee__ColoredConsole  (String content) {
		this.content = content;
	}

	
	
	public void setContent(String content) {
		setContent__wrappee__ColoredConsole(encryptionAlgorithm.encrypt(content));
	}

	

	private static EncryptionAlgorithm encryptionAlgorithm  = new Rot13();

	

	private String color;

	

	public String getColor() {
		return color;
	}

	
	
	public void setColor(String color) {
		this.color = color;
	}


}
