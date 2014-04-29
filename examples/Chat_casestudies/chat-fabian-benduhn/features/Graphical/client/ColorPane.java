package client;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.Color;

public class ColorPane extends JTextPane {

  /**
	 * 
	 */
	private static final long serialVersionUID = -4626109365581180113L;


  public void append(Color c, String s) { 
	boolean editable=this.isEditable();
	  setEditable(true);
	  if(getBackground().getRGB()==c.getRGB()){
			c=c.darker().darker();
		}
	  StyleContext sc = StyleContext.getDefaultStyleContext();
    AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
                                        StyleConstants.Foreground, c);
    
   
	
	
    int len = getDocument().getLength();
    setCaretPosition(len);  
    setCharacterAttributes(aset, false);
    replaceSelection(s); 
    setEditable(editable);
  }

}