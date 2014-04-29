package network.server.packets; 

import crypto.AbstractCiphering; 

public  class  PrivateMessage  extends UnicastPacket {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -7589201951242684519L;

	

	private String name;

	
	private String content;

	
	private String styleName;

	

	public PrivateMessage(String name, String color, String content) {
		super();
		this.name = name;
		this.styleName = color;
		this.content = content;
	}

	

	public String getName() {
		return name;
	}

	

	public void setStyleName(String sName) {
		styleName = sName;
	}

	
	
	public String getStyleName() {
		return styleName;
	}

	

	public String getContent() {
		return content;
	}

	
	
	@Override
	public void actionBeforeSend(AbstractCiphering encoder) {
		content = encoder.encode(content);
	}

	

	@Override
	public void actionAfterReceive(AbstractCiphering decoder) {
		content = decoder.decode(content);
	}


}
