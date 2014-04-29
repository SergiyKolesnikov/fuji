package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import view.GUI;

public class ViewPresenter extends Observable implements ActionListener,
		KeyListener, Observer {

	private GUI view;

	public void setThisViewPresenter(Object view) {
		this.view = (GUI)view;
	}

	private void printMessage(String msg) {
		original(msg);
		view.getChatWindow().append(msg+"\n");
		view.getInputField().setText("");

	}
	
	private void setBackTextField() {
		view.getInputField().setText("");
	}
	
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == '\n') {
			sendOutMsg(view.getInputField().getText());
		}
	}
	
	private void handleGUI(ActionEvent e) {
		handleColor(e);
		if (e.getSource().equals(view.getSendBtn())) {
			sendOutMsg(view.getInputField().getText());
		}
		if (e.getSource().equals(view.getMntmExitApplication())) {
			System.exit(1);
		}
		if (e.getSource().equals(view.getMntmPreferences())) {
			setChanged();
			notifyObservers();
		}
	}
	
	private void handleColor(ActionEvent e) {
	}

}
