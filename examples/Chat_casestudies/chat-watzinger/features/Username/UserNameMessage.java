import java.awt.Color;

public class UserNameMessage extends Message {
	
	public UserNameMessage(String source) {
		super(source);
	}
	
	public String toString() {
		return "UserNameMessage || New username: " + getSource();
	}

}