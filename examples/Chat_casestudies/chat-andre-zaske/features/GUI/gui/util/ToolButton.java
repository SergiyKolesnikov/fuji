package gui.util;

import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.JButton;

public class ToolButton  extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3597569898719238459L;

	public ToolButton(Icon icon, String text) {
		super(icon);
		setText(text);
		setMargin(new Insets(4, 4, 4, 4));
	}

	public boolean isFocusTraversable() {
		return false;
	}

	public void requestFocus() {
	}
}
