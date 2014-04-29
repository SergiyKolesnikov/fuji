package client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.StyleContext;

/**
 * TODO description
 */
public class Gui {
	private ColoredLine coloringALine(String originalLine, ColoredLine line) {
		//Second part: Parse for color Tags
		//This should Match stuff like: "Hello lovely <blue>World</blue> of hot<red>Dogs</red>."
		Pattern pattern = Pattern.compile("<([^>]+)>(.*)</\\1>");
		Matcher matcher = pattern.matcher(originalLine);
		int endPointOfPreviousTag = -1;
		Boolean firstBeforeString = true;
		while(matcher.find()){
			String color = matcher.group(1);
			
			//Before
			String before = (firstBeforeString)
					?originalLine.substring(0,matcher.start())
					:originalLine.substring(endPointOfPreviousTag, matcher.start());
			;
			firstBeforeString = false;
			endPointOfPreviousTag = matcher.end();  
			
			//Colored tag
			String coloredString = originalLine.substring(matcher.start(2),matcher.end(2));
			
			//Insertions
			if(!before.equals("")){
				line.lineParts.add(before);
				line.lineStyles.add(this.doc.getStyle(StyleContext.DEFAULT_STYLE));
			}
			
			if(!coloredString.equals("")){
				line.lineParts.add(coloredString);
				line.lineStyles.add(this.getStyleOfColor(color));
			}
		}
		return original(originalLine, line);
	}
}