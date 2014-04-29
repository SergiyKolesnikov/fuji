package swpl.chat.client;
import java.awt.Color;
import java.awt.Component;

import javax.swing.ListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;



public class ColorComboBoxRenderer extends JLabel implements ListCellRenderer {

	private static final long serialVersionUID = 2793315787890407636L;

	public ColorComboBoxRenderer() {
		setOpaque(true);
		setHorizontalAlignment(CENTER);
		setVerticalAlignment(CENTER);
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		Color color = (Color) value;
		
		((ColorComboBoxRenderer) this).setBackground(color);
		
		if (isSelected) {
			((ColorComboBoxRenderer) this).setText("x");
		} else  {
			((ColorComboBoxRenderer) this).setText("");
		}
		
		return ((ColorComboBoxRenderer) this);
	}
}