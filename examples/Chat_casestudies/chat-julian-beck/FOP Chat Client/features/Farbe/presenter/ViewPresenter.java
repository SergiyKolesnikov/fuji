package presenter;

import java.awt.Color;

public class ViewPresenter extends Observable implements ActionListener,
		KeyListener, Observer {

	private void handleGUI(ActionEvent e) {
		handleColor(e);
		original(e);
	}
	
	private void handleColor(ActionEvent e) {
		if (e.getSource().equals(view.getBlackBtn())) {
			view.getChatWindow().setForeground(Color.BLACK);
		 }
		 if (e.getSource().equals(view.getBlueBtn())) {
			view.getChatWindow().setForeground(Color.BLUE);
		 }
		 if (e.getSource().equals(view.getRedBtn())) {
			 view.getChatWindow().setForeground(Color.RED);
		 }
		 if (e.getSource().equals(view.getGreenBtn())) {
			 view.getChatWindow().setForeground(new Color(0x005500));
		 }
	}
}