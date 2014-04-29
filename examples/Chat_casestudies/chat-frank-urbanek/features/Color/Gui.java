/**
 * TODO description
 */
import java.awt.Color;

public class Gui {
	private ColorPanel colorPanel;

	private void initGUI() {
		original();
		try {
			colorPanel = new ColorPanel();
			getContentPane().add(colorPanel, BorderLayout.EAST);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void processMessage(TextMessage msg){
		msg.addProperty("textColor", colorPanel.getTextColor());
		original(msg);
	}
	
	private String CreateFormattedText(String text, TextMessage msg) {
		Color color = (Color)msg.getProperty("textColor");
		if (color !=null){
			text = String.format("<%X>%s</%X>", color.getRGB(), text, color.getRGB());
		}
		return text;
	}
	
}