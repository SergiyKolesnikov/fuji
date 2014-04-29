
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;


public class ColorPanel extends JPanel {

	private static final long serialVersionUID = 8233925818612694382L;

	private ButtonGroup buttonGroup1;
	private JRadioButton jRadioButton1;
	private JRadioButton jRadioButton3;
	private JRadioButton jRadioButton2;

	private Color textColor;

	public ColorPanel() {
		textColor = Config.TEXTCOLOR_CLIENT_DEFAULT;
		FlowLayout jPanel2Layout = new FlowLayout();

		this.setLayout(jPanel2Layout);
		this.setPreferredSize(new java.awt.Dimension(180, 239));
		this.setBorder(BorderFactory.createTitledBorder(null, "Color",
				TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION));
		this.add(getJRadioButton1());
		this.add(getJRadioButton2());
		this.add(getJRadioButton3());

		for (Enumeration<AbstractButton> e = buttonGroup1.getElements(); e
				.hasMoreElements();) {
			AbstractButton b = e.nextElement();
			b.addActionListener(radioButtonListener());
			b.setActionCommand(b.getName());
		}
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	private ActionListener radioButtonListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JRadioButton b = (JRadioButton) e.getSource();
				if (b.getText().equals("red")) {
					setTextColor(Color.RED);
				} else if (b.getText().equals("blue")) {
					setTextColor(Color.BLUE);
				} else if (b.getText().equals("black")) {
					setTextColor(Color.BLACK);
				}
			}
		};
	}

	private ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
		}
		return buttonGroup1;
	}

	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setText("red");
			// jRadioButton1.se
			getButtonGroup1().add(jRadioButton1);
		}
		return jRadioButton1;
	}

	private JRadioButton getJRadioButton2() {
		if (jRadioButton2 == null) {
			jRadioButton2 = new JRadioButton();
			jRadioButton2.setText("blue");
			getButtonGroup1().add(jRadioButton2);
		}
		return jRadioButton2;
	}

	private JRadioButton getJRadioButton3() {
		if (jRadioButton3 == null) {
			jRadioButton3 = new JRadioButton();
			jRadioButton3.setText("black");
			getButtonGroup1().add(jRadioButton3);
		}
		return jRadioButton3;
	}

}
