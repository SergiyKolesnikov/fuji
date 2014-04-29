package gui.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicButtonUI;

public class ButtonTabComponent extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8987137124836582367L;
	private JButton button;
	private static Color COLOR_OVERBT_BG = new Color(160, 160, 160);
	private static Color COLOR_OVERBT_BORDER = new Color(100, 100, 100);

	public ButtonTabComponent(String title, ImageIcon icon) {
		// unset default FlowLayout' gaps
		super(new FlowLayout(FlowLayout.LEFT, 0, 0));
		setOpaque(false);
		// Tab Title
		JLabel label = new JLabel(title);
		// add more space between the label and the button
		// label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
		label.setIcon(icon);
		add(label);
		// tab button
		button = new TabButton();
		add(button);
		// add more space to the top of the component
		setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
	}

	public void addActionListener(ActionListener a) {
		button.addActionListener(a);
	}

	private class TabButton extends JButton {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1368163340576552256L;

		public TabButton() {
			int size = 18;
			setPreferredSize(new Dimension(size, size));
			setToolTipText("Schlieﬂen");
			// Make the button looks the same for all Laf's
			setUI(new BasicButtonUI());
			// Make it transparent
			setContentAreaFilled(false);
			// No need to be focusable
			setFocusable(false);
			// Making nice rollover effect
			// we use the same listener for all buttons
			setRolloverEnabled(true);
			// Close the proper tab by clicking the button
		}

		// we don't want to update UI for this button
		public void updateUI() {
		}

		// paint the cross
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g.create();
			// shift the image for pressed buttons
			if (getModel().isPressed()) {
				g2.translate(1, 1);
			}

			g2.setStroke(new BasicStroke(2));
			g2.setColor(Color.GRAY);
			int delta = 4;
			if (getModel().isRollover()) {
				g2.setColor(COLOR_OVERBT_BG);
				g2.fillRect(0, 0, getWidth(), getHeight());
				g2.setColor(COLOR_OVERBT_BORDER);
				g2.drawRect(0, 0, getWidth(), getHeight());
				g2.setColor(Color.BLACK);
				delta = 5;
			}

			g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight()
					- delta - 1);
			g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight()
					- delta - 1);

			g2.dispose();
		}
	}

	
}
