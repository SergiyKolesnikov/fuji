package gui.util; 

import java.awt.Dimension; 
import java.awt.Image; 
import java.awt.Toolkit; 
import java.awt.image.BufferedImage; 
import java.io.File; 
import java.io.IOException; 
import java.net.URL; 

import javax.imageio.ImageIO; 
import javax.swing.ImageIcon; 
import javax.swing.JOptionPane; 

public  class  GuiTLH {
	
	private static final Dimension screenSize = Toolkit.getDefaultToolkit()
			.getScreenSize();

	

	private GuiTLH() {
	}

	

	public static Dimension getscreenSize() {
		return screenSize;
	}

	

	public static int getscreenWidth() {
		return screenSize.width;
	}

	

	public static int getscreenHeight() {
		return screenSize.height;

	}

	

	public static ImageIcon loadImageIcon(String filename) {
		ImageIcon image = null;
		try {
			URL url = GuiTLH.class
					.getResource("/gui/images/" + filename);
			if (url != null) {
				java.awt.Image img = Toolkit.getDefaultToolkit().createImage(
						url);
				if (img != null)
					image = new ImageIcon(img);
			}
		} catch (Throwable e) {
			JOptionPane.showMessageDialog(null,
					"Fehler beim laden des lokalen Bildes " + filename + ": "
							+ e.getMessage(), "Fehler bei der Eingabe",
					JOptionPane.ERROR_MESSAGE);
		}
		return image;
	}

	
	public static Image loadImage(String filename) {
		Image image = null;
		try {
			URL url = GuiTLH.class
					.getResource("/gui/images/" + filename);
			if (url != null) {
				image = ImageIO.read(url);
			}
		} catch (Throwable e) {
			JOptionPane.showMessageDialog(null,
					"Fehler beim laden des lokalen Bildes " + filename + ": "
							+ e.getMessage(), "Fehler bei der Eingabe",
					JOptionPane.ERROR_MESSAGE);
		}
		return image;
	}

	

	public static BufferedImage loadLocalImage(String filename)
			throws IOException {
		return ImageIO.read(new File(filename));
	}

	

	public static BufferedImage loadWebImage(String filename)
			throws IOException {
		return ImageIO.read(new java.net.URL(filename));
	}

	

	public static boolean isImageFile(String path) {
		return path != null
				&& (path.toLowerCase().endsWith(".jpg")
						|| path.toLowerCase().endsWith(".jpeg")
						|| path.toLowerCase().endsWith(".gif") || path
						.toLowerCase().endsWith(".png"));
	}


}
