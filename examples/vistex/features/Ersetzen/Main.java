





public class Main {
	 private javax.swing.JMenuItem ersetzItem;
	 private void doTheMenuBars() {
		 original();
		 ersetzItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
	        ersetzItem.setText("Ersetzen");
	        ersetzItem.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                 new Ersetzen(s.editorPane);
	            }
	        });
	        editMenu.add(ersetzItem);
	 }
	 private void initMenuBarItems(){
		 original();
		 ersetzItem = new javax.swing.JMenuItem();
	 }
}
