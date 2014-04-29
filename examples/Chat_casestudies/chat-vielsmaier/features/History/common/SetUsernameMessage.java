package common;

public class SetUsernameMessage extends Message {
	public String textForLog() {
		return oldName + " changed his name to " + newName;
	}
	
}