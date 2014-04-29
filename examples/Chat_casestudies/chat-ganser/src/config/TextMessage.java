import java.io.Serializable; 
public   class  TextMessage  implements Serializable {
	

	private static final long serialVersionUID = -9161595018411902079L;

	
	private String content;

	

	public TextMessage(String content) {
		super();
		this.content = content;
	}

	

	public String getContent() {
		return content;
	}

	
    private String color;

	
    
    public void setColor(String color) {
	this.color = color;
    }

	
    
    public String getColor() {
	return this.color;
    }


}
