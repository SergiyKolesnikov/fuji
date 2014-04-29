package gui.util; 

import java.awt.Color; 
import java.util.HashMap; 

import javax.swing.text.Style; 
import javax.swing.text.StyleConstants; 
import javax.swing.text.StyleContext; 

public  class  ChatStyles {
	

	private HashMap<String, Style> styles;

	

	public static String defaultStyleName = "default";

	

	private StyleContext sc;

	

	private String[] names = new String[] { "Aquamarine", "Brown", "Chartreuse",
			"Chocolate", "Cornsilk", "Darkgoldenrod", "Darkgreen",
			"Darkmagenta", "Darkorchid", "Dodgerblue", "Firebrick",
			"Forestgreen", "Gold", "Lightgoldenrod", "Limegreen",
			"Mediumpurple", "Orangered", "Paleturquoise", "Peru",
			"Rosybrown", "Steelblue", "Tomato", "Violet", "Violetred" };

	

	private Style defaultStyle;

	

	public ChatStyles() {
		styles = new HashMap<String, Style>();
		sc = new StyleContext();
		defaultStyle = sc.getStyle(StyleContext.DEFAULT_STYLE);
		styles.put(defaultStyleName, defaultStyle);
		generateStyle("blue", Color.blue, 12, true);
		generateStyle("red", Color.red, 12, true);
		generateStyle("private", Color.darkGray, 12, true);
		generateStyles();
	}

	

	private void generateStyle(String name, Color color, int size, boolean bold) {
		Style style = sc.addStyle(name, defaultStyle);
		// Set Attributes
		style.addAttribute(StyleConstants.Foreground, color);
		style.addAttribute(StyleConstants.FontSize, size);
		style.addAttribute(StyleConstants.FontFamily, "serif");
		style.addAttribute(StyleConstants.Bold, new Boolean(bold));
		// add Style
		styles.put(name, style);
	}

	

	public Style getDefaultStyle() {
		return defaultStyle;
	}

	

	public Style getStyle(String name) {
		Style style = styles.get(name);
		if (style == null) {
			return defaultStyle;
		}
		return style;
	}

	
	
	public String[] getStyleList(){
		return names;
	}

	

	private void generateStyles() {
		Color[] farben = new Color[] { new Color(69, 139, 116),
				new Color(165, 42, 42), new Color(69, 139, 0),
				new Color(210, 105, 30), new Color(139, 136, 120),
				new Color(255, 185, 15), new Color(0, 100, 0),
				new Color(139, 0, 139), new Color(153, 50, 204),
				new Color(30, 144, 255), new Color(178, 34, 34),
				new Color(34, 139, 34), new Color(255, 215, 0),
				new Color(139, 129, 76), new Color(50, 205, 50),
				new Color(93, 71, 139), new Color(255, 69, 0),
				new Color(102, 139, 139), new Color(205, 133, 63),
				new Color(188, 143, 143), new Color(54, 100, 139),
				new Color(255, 99, 71), new Color(238, 130, 238),
				new Color(208, 32, 144) };
		for(int i=0; i< names.length; i++){
			generateStyle(names[i], farben[i], 12, true);
		}
	}


}
