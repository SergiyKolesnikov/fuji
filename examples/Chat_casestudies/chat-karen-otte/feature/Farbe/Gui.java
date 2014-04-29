import javax.swing.text.SimpleAttributeSet;


/**
 * TODO description
 */
public class Gui {
	
	StringFormater formater = new StringFormater();
	
	public void ShowStyledChatline(String line, SimpleAttributeSet attributes){
		attributes = formater.checkLineTags(line);
		original(line, attributes);
	
	}

}