import java.awt.Button;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

/**
 * TODO description
 */
public class Main {
	 private javax.swing.JMenuItem suchItem;
	 private void doTheMenuBars()  {
		 original();
		 suchItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
	        suchItem.setText("Such");
	        suchItem.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                 new Suchen(s.editorPane);
	            }
	        });
	        editMenu.add(suchItem);
	 }
	 
	 private void initMenuBarItems(){
		 original();
		 suchItem = new javax.swing.JMenuItem();
		 
	 }
}
