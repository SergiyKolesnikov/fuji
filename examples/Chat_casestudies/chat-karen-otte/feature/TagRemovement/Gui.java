import javax.swing.text.SimpleAttributeSet;

/**
 * TODO description
 */
public class Gui {

	public void ShowStyledChatline(String line, SimpleAttributeSet attributes){		
		 try{		
			while (line.contains("<") && line.contains(">")){
				int beginTag = line.indexOf("<");
				int endTag = line.indexOf(">") +1;
						
				line = line.substring(0, beginTag) + line.substring(endTag);				
			}
										
		}catch(IndexOutOfBoundsException e){
			System.out.println("String ist zu kurz um Tags zu eliminieren");
			System.out.println(line);
		}
		original(line, attributes);
	}
}