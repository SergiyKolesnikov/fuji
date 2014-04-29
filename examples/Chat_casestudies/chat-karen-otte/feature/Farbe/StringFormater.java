import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
/**
 * HelperClass with static Methods for Stringwork
 * @author Karen Otte
 *
 */

public class StringFormater {
	Map <String, Color> Colorlist;
	
	public StringFormater(){
		Colorlist = initializeColorList();
	}

	/**
	 * Method initialize a Map with german and englisch colorwords as keys and the awt.Color als value
	 */
	public Map<String, Color> initializeColorList(){
		Map <String, Color> colorList = new HashMap<String, Color>();
		colorList.put("blue", Color.blue);
		colorList.put("blau", Color.blue);
		colorList.put("red", Color.red);
		colorList.put("rot", Color.red);
		colorList.put("green", Color.green);
		colorList.put("gruen", Color.green);
		colorList.put("cyan", Color.cyan);
		colorList.put("magenta", Color.magenta);
		colorList.put("yellow", Color.yellow);
		colorList.put("gelb", Color.yellow);
		colorList.put("grey", Color.gray);
		colorList.put("grau", Color.gray);
		
		return colorList;
	}

	public boolean italicTagFound(String line) {
		if ((line.toLowerCase().contains("<itelic>") && line.toLowerCase().contains("</itelic>"))
				|| (line.toLowerCase().contains("<i>") && line.toLowerCase().contains("</i>")))
			return true;
		return false;
	}
	
	public boolean boldTagFound(String line) {
		if ((line.toLowerCase().contains("<bold>") && line.toLowerCase().contains("</bold>"))
			|| (line.toLowerCase().contains("<b>") && line.toLowerCase().contains("</b>")))
				return true;
		return false;
	}

	/**
	 * Method checks which Tags are given in Message and try to parse them
	 * @param line us the TextLine as Strings containing optional Tags
	 */
	public SimpleAttributeSet checkLineTags(String line) {
				
		SimpleAttributeSet set = new SimpleAttributeSet();

		// parsen der FarbTags
		int colorTagCounter =0;
		String colorKey = "";
		for(String key : Colorlist.keySet()){
			if (line.toLowerCase().contains("<" + key + ">") && line.toLowerCase().contains("</" + key + ">")){
				colorTagCounter++;
				colorKey = key;
			}
		}
		
		// setzen der Farbe
		if (!colorKey.isEmpty() && colorKey != null){
			StyleConstants.setForeground(set, Colorlist.get(colorKey));
		}
		
		// setzen des Italic-Styles
		StyleConstants.setItalic(set, italicTagFound(line));
		// setzen des Bold-Styles
		StyleConstants.setBold(set, boldTagFound(line));
		
		
		// Write Hints and Exceptions in Output
		if (colorTagCounter > 1){
			System.out.println("Only the first ColorTag is Used!");
		}
		if (colorTagCounter == 0){
			System.out.println("No Colortag found or color not implemented");
		}		
		return set;
	}
}