import javax.swing.JOptionPane;
import javax.swing.undo.UndoManager;



public class Main {
	 private javax.swing.JMenuItem undoItem;
	 private UndoManager undoMg;
	 public void initComponents()  {
		 original();
		 undoMg = new UndoManager();
		 editorPane.getDocument().addUndoableEditListener(undoMg);
		 undoItem = new javax.swing.JMenuItem();
		
	        undoItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
	        undoItem.setText("Undo");
	        undoItem.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                undoItemActionPerformed(evt);
	            }
	        });
	        editMenu.add(undoItem);
	        jMenuBar1.add(editMenu);
	       
	}
	    private void undoItemActionPerformed(java.awt.event.ActionEvent evt) {

            if(undoMg.canUndo()) {
                undoMg.undo();
            } else {
                JOptionPane.showMessageDialog(null,"can not Undo","Warning",JOptionPane.WARNING_MESSAGE);
            }
        
	    }

}