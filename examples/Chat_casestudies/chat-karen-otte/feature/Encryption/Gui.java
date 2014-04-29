import javax.swing.text.SimpleAttributeSet;

/**
 * TODO description
 */
public class Gui {
	
	public void ShowStyledChatline(String line, SimpleAttributeSet attributes){
		EncryptionPlugin encryption = new EncryptionPlugin(EncryptionPlugin.Encoding.Rot13);
		line = encryption.handleInputMessage(line);
		original(line, attributes);
	}
	
	public String formatOutput(String line){
		EncryptionPlugin encryption = new EncryptionPlugin(EncryptionPlugin.Encoding.Rot13);
		line = encryption.handleOutputMessage(line);
		return line;
	}

}