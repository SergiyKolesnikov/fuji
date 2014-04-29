import java.io.File;

import javax.swing.JMenu;

/**
 * TODO description
 */
public class Main {
	private javax.swing.JMenuItem saveItem;
	private void doTheMenuBars() {
	
		original();
	    saveItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
	    saveItem.setText("Speichern als Text");
	    saveItem.addActionListener(new java.awt.event.ActionListener() {
	        public void actionPerformed(java.awt.event.ActionEvent evt) {
	            saveItemActionPerformed(evt);
	        }
	    });
	    fileMenu.add(saveItem);
	
	}
	
	private void initMenuBarItems() {
		original();
	    saveItem = new javax.swing.JMenuItem();

	}
 
    
    protected void saveDialog(File f) {
        saveDialog(f, saveTextField(), "txt");
    }
    private void saveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveItemActionPerformed
        saveDialog(null, null, "txt");
    }//GEN-LAST:event_saveItemActionPerformed

}