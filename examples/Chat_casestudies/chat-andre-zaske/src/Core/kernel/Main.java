package kernel; 

import gui.Gui; 
import gui.util.SplashScreen; 

import javax.swing.SwingUtilities; 

/**
 * TODO description
 */
public   class  Main {
	

	public Main(String[] args) {
		startup();
		init();
		finish();
	}

	

	private void startup  () {
		// Init Splashscreen
		splashsreen = new SplashScreen();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				splashsreen.setVisible(true);
			}
		});
	}

	

	private void init() {

	}

	

	private void finish  () {
		// Init Gui
		gui = new Gui();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				gui.createAndShow();
				if (splashsreen != null) {
					splashsreen.setVisible(false);
				}
			}
		});
	}

	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Main(args);
	}

	

	private Gui gui;

	
	private SplashScreen splashsreen;


}
