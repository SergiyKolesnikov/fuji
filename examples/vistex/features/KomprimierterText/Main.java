import java.io.File;

import javax.swing.JMenu;

/**
 * TODO description
 */
public class Main {
	 private javax.swing.JMenuItem jMenuItem2;
	private void doTheMenuBars() {
	
		original();
	    jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
	    jMenuItem2.setText("komprimierter Text");
	    jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
	        public void actionPerformed(java.awt.event.ActionEvent evt) {
	            jMenuItem2ActionPerformed(evt);
	        }
	    });
	    exportMenu.add(jMenuItem2);
	
	}
	private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        saveDialog(null, null, "zipped");
    }//GEN-LAST:event_jMenuItem2ActionPerformed
	
	private void initMenuBarItems() {
		original();
	    jMenuItem2 = new javax.swing.JMenuItem();

	}

}