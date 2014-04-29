import javax.swing.JMenuItem;


/**
 * TODO description
 */
public class Main {
	private JMenuItem formatMenu_Font;
	private void doTheMenuBars()  {
		original();
		
		formatMenu_Font.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
		formatMenu_Font.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	editorPane.requestFocus();
            	new FontDialog(s.editorPane);
            }
        });
		formatMenu.add(formatMenu_Font); 
	}
	 private void initMenuBarItems(){
		 original();
		 formatMenu_Font = new JMenuItem("Schriftgroesse...");
	 }
}