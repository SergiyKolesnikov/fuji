package gui.util; 

import java.awt.Dimension; 
import java.awt.Graphics; 

import javax.swing.ImageIcon; 
import javax.swing.JPanel; 
import javax.swing.JWindow; 


public  class  SplashScreen  extends JWindow {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8036235580698742382L;

	
	
	public SplashScreen() {
		getContentPane().add(new SplashPanel());
		pack();
		Dimension size = getSize();
		setLocation(GuiTLH.getscreenWidth() / 2 - size.width / 2,
				GuiTLH.getscreenHeight() / 2 - size.height / 2);
	}

	
	
	
	private  class  SplashPanel  extends JPanel {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 4375052457594091078L;

		
		private ImageIcon splashImage = GuiTLH.loadImageIcon("splashscreen.png");

		
		private Dimension size = new Dimension(splashImage.getIconWidth(),
				splashImage.getIconHeight());

		

		public SplashPanel() {
			super();
		}

		

		public Dimension getPreferredSize() {
			return size;
		}

		

		public void paint(Graphics g) {
			splashImage.paintIcon(this, g, 0, 0);
		}


	}


}
