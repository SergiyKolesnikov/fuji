package view;

import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GUI {

	private JButton redBtn;
	private JButton blueBtn;
	private AbstractButton greenBtn;
	private AbstractButton blackBtn;
	
	private void addColor(ActionListener presenter) {
		redBtn = new JButton("");
		redBtn.setIcon(new ImageIcon(getClass().getResource(
				"/resources/redBtnIcon.png")));
		redBtn.setText("");
		redBtn.setBounds(10, 270, 25, 23);
		redBtn.addActionListener(presenter);
		frame.getContentPane().add(redBtn);
		
		blueBtn = new JButton("");
		blueBtn.setIcon(new ImageIcon(getClass().getResource(
				"/resources/blueBtnIcon.png")));
		blueBtn.setText("");
		blueBtn.setBounds(40, 270, 25, 23);
		blueBtn.addActionListener(presenter);
		frame.getContentPane().add(blueBtn);
		
		greenBtn = new JButton("");
		greenBtn.setIcon(new ImageIcon(getClass().getResource(
				"/resources/greenBtnIcon.png")));
		greenBtn.setText("");
		greenBtn.setBounds(70, 270, 25, 23);
		greenBtn.addActionListener(presenter);
		frame.getContentPane().add(greenBtn);
		
		blackBtn = new JButton("");
		blackBtn.setIcon(new ImageIcon(getClass().getResource(
				"/resources/blackBtnIcon.png")));
		blackBtn.setBounds(100, 270, 25, 23);
		blackBtn.setText("");
		blackBtn.addActionListener(presenter);
		frame.getContentPane().add(blackBtn);
	}
	
	public JButton getRedBtn() {
		return redBtn;
	}

	public JButton getBlueBtn() {
		return blueBtn;
	}

	public AbstractButton getGreenBtn() {
		return greenBtn;
	}

	public AbstractButton getBlackBtn() {
		return blackBtn;
	}
}