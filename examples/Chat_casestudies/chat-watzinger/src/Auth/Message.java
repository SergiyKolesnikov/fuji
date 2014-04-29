import java.io.Serializable; 

public abstract  class  Message  implements Serializable {
	

	private static final long serialVersionUID = 4662817282076859092L;

	
	protected String source = "Unknown";

	

	public Message(String source) {
		this.source = source;
	}

	

	public String getSource() {
		return source;
	}

	

	public void setSource(String source) {
		this.source = source;
	}


}
