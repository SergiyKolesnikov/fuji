
import java.awt.Choice;
import java.awt.Event;
import java.awt.Frame;

public class Gui {

	private Choice colorChoice;
	
	private void createContents() {
		original();
		
		colorChoice = new Choice();
		
		for (int i = 0; i < Color.COLORS.length; i++)
			colorChoice.add(Color.COLORS[i]);
		
		add("West", colorChoice);
	}

	public boolean handleEvent(Event e) {
		if (e.target == colorChoice && e.id == Event.ACTION_EVENT) {
			Color.instance.setColor(colorChoice.getSelectedItem());
			return true;
		}
		
		return original(e);
	}
	
	public void newChatLine(TextMessage line) {
		original(MessageFactory.newTextMessage(line.getContent() + " [" + line.getColor() + "]"));
	}

}
