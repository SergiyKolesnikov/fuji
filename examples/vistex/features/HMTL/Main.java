import java.io.File;

import javax.swing.JMenu;

/**
 * TODO description
 */
public class Main {
    private javax.swing.JMenuItem jMenuItem1;
    
	private void doTheMenuBars() {
	
		original();
	    jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
	    jMenuItem1.setText("HTML");
	    jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
	        public void actionPerformed(java.awt.event.ActionEvent evt) {
	            jMenuItem1ActionPerformed(evt);
	        }
	    });
	    
	    exportMenu.add(jMenuItem1);
	
	}

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
        saveHTMLDialog(Utils.exportHTML(editorPane.getText(), currentTitle));
    }
    protected void saveHTMLDialog(String text) {
        saveDialog(null, text, "html");
    }
	private void initMenuBarItems() {
		original();
	    jMenuItem1 = new javax.swing.JMenuItem();
	}


}