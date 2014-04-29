package common; 

public   class  SetUsernameMessage  extends Message {
	
	public String textForLog() {
		return oldName + " changed his name to " + newName;
	}

	
	private String oldName;

	
	private String newName;

	
	
	public SetUsernameMessage(String o, String n) {
		oldName = o;
		newName = n;
	}

	
	
	public String getOldName() {
		return oldName;
	}

	
	
	public String getNewName() {
		return newName;
	}


}
