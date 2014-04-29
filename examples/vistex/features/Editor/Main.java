public class Main  extends javax.swing.JFrame {

    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JMenu exportMenu;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu hashMenu;
    private javax.swing.JMenuItem openItem;
 	
	private void doTheLayout() {
		member++;
		original();
		this.add(jScrollPane1);
	}
	
	private void doTheMenuBars() {
		original();
		fileMenu.setText("Datei");
		
	    jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
	    jMenuItem3.setText("Neu");
	    jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
	        public void actionPerformed(java.awt.event.ActionEvent evt) {
	            new_document(evt);
	        }
	    });
	    fileMenu.add(jMenuItem3);
	
	    openItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
	    openItem.setText("Öffnen");
	    openItem.addActionListener(new java.awt.event.ActionListener() {
	        public void actionPerformed(java.awt.event.ActionEvent evt) {
	            openItemActionPerformed(evt);
	        }
	    });
	    fileMenu.add(openItem);
	

	
	    exitItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
	    exitItem.setText("Beenden");
	    exitItem.addActionListener(new java.awt.event.ActionListener() {
	        public void actionPerformed(java.awt.event.ActionEvent evt) {
	            closeClicked(evt);
	        }
	    });
		
	    fileMenu.add(exitItem);
	
	 
	

	

	    
	    hashMenu.setText("Hash");
	    hashMenu.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseClicked(java.awt.event.MouseEvent evt) {
	            hashMenuMouseClicked(evt);
	        }
	    });
	    
	    jMenuBar1.add(fileMenu);

	    jMenuBar1.add(hashMenu);	    
	}
	
	private void initMenuBarItems() {
		fileMenu = new javax.swing.JMenu();
	    hashMenu = new javax.swing.JMenu();

	    jMenuItem3 = new javax.swing.JMenuItem();
	    
	    openItem = new javax.swing.JMenuItem();
	    exitItem = new javax.swing.JMenuItem();

	}
	
	private static int getMembers() {
		return member++;
	}
	
}