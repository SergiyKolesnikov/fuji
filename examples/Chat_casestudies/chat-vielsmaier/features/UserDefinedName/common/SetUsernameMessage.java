package common;

public class SetUsernameMessage extends Message {
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