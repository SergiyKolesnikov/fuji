public class TextMessage {
	private Color color;
	
	public void setColor(Color c) {
		this.color = c;
	}
	
	public Color getColor() {
		return this.color;
	}
		
	public String getContentWithColor() {
		if (color != null)
			return color.getPrefix() + getContent() + color.getSuffix();
		else
			return getContent();
	}

}