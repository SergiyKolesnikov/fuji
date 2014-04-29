import java.awt.Color;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * TODO description
 */
public class Main {
	private JMenu formatMenu_Color; 
	private JMenuItem formatMenu_Color_FgColor,formatMenu_Color_BgColor;
	private void doTheMenuBars()  {
		 	original();
			formatMenu_Color_FgColor.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	editorPane.requestFocus();
					Color color=JColorChooser.showDialog(s,"Schriftfarbe",Color.black);
					if(color!=null)
					{
						editorPane.setForeground(color);
					} 
					else
						return;
	            }
	        });
			
			formatMenu_Color_BgColor.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	editorPane.requestFocus();
					Color color=JColorChooser.showDialog(s,"Hintergrundfarbe",Color.white);
					if(color!=null)
					{
						editorPane.setBackground(color);
					} 
					else
						return;
	            }
	        });
			formatMenu.addSeparator();
			formatMenu.add(formatMenu_Color);
			formatMenu_Color.add(formatMenu_Color_FgColor);
			formatMenu_Color.add(formatMenu_Color_BgColor);
			jMenuBar1.add(formatMenu);
		 
	 }
	 private void initMenuBarItems(){
		 original();
		 formatMenu_Color = new JMenu("Farbe"); 
		 formatMenu_Color_FgColor=new  JMenuItem("Schrift Farbe");
		 formatMenu_Color_BgColor=new JMenuItem("Hintergrund Farbe");
		 
	 }
}