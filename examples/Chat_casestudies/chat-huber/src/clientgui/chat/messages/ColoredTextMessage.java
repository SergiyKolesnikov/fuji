package chat.messages; 

import chat.IMessageVisitor; 

public  class  ColoredTextMessage  extends DefaultTextMessage {
	
	private String color;

	

	public ColoredTextMessage(String content, String color) {
		super(content);
		this.color = color;
	}

	

	public String getColor() {
		return color;
	}

	

	public void setColor(String color) {
		this.color = color;
	}

	

	@Override
	public void accept(IMessageVisitor visitor) {
		visitor.visit(this);
	}


}
