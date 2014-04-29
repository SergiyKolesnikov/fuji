package gui.util; 

import java.awt.Dimension; 
import java.awt.Graphics; 
import java.awt.Image; 
import java.beans.PropertyChangeEvent; 
import java.beans.PropertyChangeListener; 
import java.io.File; 
import java.io.IOException; 

import javax.swing.JFileChooser; 
import javax.swing.JPanel; 

/**
 * @author Andre
 */
public  class  ImagePanel  extends JPanel  implements PropertyChangeListener {
	

	/**
	 * 
	 */
	public static final int ReSizeOnHeight = 1;

	
	public static final int ReSizeOnWidth = 2;

	

	private static final long serialVersionUID = 3066416589982094100L;

	
	private Image img = null;

	
	private Image thumbnail = null;

	
	private Dimension size;

	
	private int resizeOption = 0;

	

	public ImagePanel(int heigth, int width) {
		init(heigth, width);
	}

	

	public ImagePanel(Image image, int heigth, int width) {
		init(heigth, width);
		setImage(image);
	}

	

	public int getResizeOption() {
		return resizeOption;
	}

	

	public void setResizeOption(int resizeOption) {
		this.resizeOption = resizeOption;
	}

	

	private void init(int heigth, int width) {
		size = new Dimension(heigth, width);
		setLayout(null);
		setDSize(size);
	}

	

	private void setDSize(Dimension dim) {
		setPreferredSize(dim);
		setMinimumSize(dim);
		setMaximumSize(dim);
	}

	

	public void setImage(Image image) {
		if (image != null) {
			img = image;
			Dimension imgwidth = new Dimension(img.getWidth(null),
					img.getHeight(null));
			double scale = 1.0;
			if (resizeOption == 1) {
				scale = (size.height * 1.0) / (imgwidth.height * 1.0);
			} else if (resizeOption == 2) {
				scale = (size.width * 1.0) / (imgwidth.width * 1.0);
			} else {
				if (imgwidth.width > imgwidth.height) {
					scale = (size.width * 1.0) / (imgwidth.width * 1.0);
				} else {
					scale = (size.height * 1.0) / (imgwidth.height * 1.0);
				}
			}
			imgwidth.width = (int) Math.floor(imgwidth.width * scale);
			imgwidth.height = (int) Math.floor(imgwidth.height * scale);

			setDSize(imgwidth);
			thumbnail = img.getScaledInstance(imgwidth.width, imgwidth.height,
					Image.SCALE_SMOOTH);
		}
	}

	

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (thumbnail != null)
			g.drawImage(thumbnail, 0, 0, null);
	}

	

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		String propertyName = e.getPropertyName();

		// Make sure we are responding to the right event.
		if (propertyName.equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
			File selection = (File) e.getNewValue();
			String name;

			if (selection == null)
				return;
			else
				name = selection.getAbsolutePath();

			/*
			 * Make reasonably sure we have an image format that AWT can handle
			 * so we don't try to draw something silly.
			 */
			if (GuiTLH.isImageFile(name)) {
				try {
					setImage(GuiTLH.loadLocalImage(name));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				repaint();
			}
		}

	}


}
