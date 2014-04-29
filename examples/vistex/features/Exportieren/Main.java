import java.io.File;

import javax.swing.JMenu;

/**
 * TODO description
 */
public class Main {
	private javax.swing.JMenu exportMenu;
	private void doTheMenuBars() {
	
		original();
	    exportMenu.setText("Exportieren als...");
	    jMenuBar1.add(exportMenu);
	
	}
	
	private void initMenuBarItems() {
		original();
		exportMenu = new javax.swing.JMenu();


	}



}