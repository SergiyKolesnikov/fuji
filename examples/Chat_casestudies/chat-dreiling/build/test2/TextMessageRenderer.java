
package test2;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import java.awt.Component;

import java.awt.Color;



abstract class TextMessageRenderer$$GUI extends JLabel implements ListCellRenderer {

		  public Component getListCellRendererComponent(JList list, Object value, int index,
		      boolean isSelected, boolean cellHasFocus) {
		    
			  if (value instanceof TextMessage) {
				  setText(((TextMessage)value).getSender() + ">"+((TextMessage)value).getContent());
			  }
			  else {
				  setText(value.toString());  
			  }
		 
		    return ((TextMessageRenderer) this);
		  }
	}



public class TextMessageRenderer extends  TextMessageRenderer$$GUI   {

 		public Component getListCellRendererComponent(JList list, Object value, int index,
		      boolean isSelected, boolean cellHasFocus) {
		    
			  if (value instanceof TextMessage) {
				
				  /*if[COLOR]*/
				  String col = ((TextMessage)value).getSetting(Utils.COLORKEY);
				  if (col != null)
					  setForeground(new Color(Integer.parseInt(col)));
				  else
					  setForeground(Color.BLACK);
				  /*end[COLOR]*/
			  }
			//return this;
		    return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		  }

}