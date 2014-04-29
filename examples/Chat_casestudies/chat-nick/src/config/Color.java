

import java.io.Serializable;  

public   class   Color   implements Serializable {
	
	

	private static final long serialVersionUID = 913565818891280171L;

	
	
	private String name;

	

	public Color(String name) {
		this.name = name;
	}

	

	public String toString() {
		return name;
	}

	

	
	public String getPrefix() {
		if (name.equals("Schwarz"))
			return "";
		return "<"+name+">";
	}

	
	
	public String getSuffix() {
		if (name.equals("Schwarz"))
			return "";		
		return "</"+name+">";
	}


}
