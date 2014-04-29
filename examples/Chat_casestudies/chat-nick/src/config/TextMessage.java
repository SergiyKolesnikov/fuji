
import java.io.Serializable; public   class  TextMessage  implements Serializable {
	

	private static final long serialVersionUID = -9161595018411902079L;

	
	private String content;

	

	public TextMessage(String content) {
		super();
		this.content = content;
	}

	

	public void setContent(String val) {
		this.content = val;
	}

	
	
	public String getContent() {
		return content;
	}

	
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
