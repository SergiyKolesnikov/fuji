package gui.panels;

import java.awt.LayoutManager;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import gui.Gui;

public abstract class AbstractTabPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5640806220984327764L;
	private Gui gui;
	private ImageIcon icon;

	public AbstractTabPanel(LayoutManager layout, Gui g) {
		super(layout);
		gui = g;
		icon = null;
	}

	public void setIcon(ImageIcon img) {
		icon = img;
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public Gui getGui() {
		return gui;
	}

	public void addTabToPanel(JTabbedPane jtpane) {
		jtpane.addTab(this.getName() + "    ", this.getIcon(), this,
				this.getToolTipText());
	}

	public void repaintGui() {
		gui.validate();
		gui.repaint();
	}

	public abstract void refresh();

	public abstract void onEntering();

	public abstract void onLeaving();
	
	public abstract void onClose();
}
