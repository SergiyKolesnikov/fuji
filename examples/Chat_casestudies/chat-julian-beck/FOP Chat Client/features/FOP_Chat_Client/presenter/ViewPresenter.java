package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.Observable;
import java.util.Observer;

import model.Application;
import model.Client;

import java.net.Socket;

public class ViewPresenter extends Observable implements ActionListener,
		KeyListener, Observer {
	
	private Client client = null;


	public ViewPresenter(Object view, Socket socket, Application app) {
		setThisViewPresenter(view);
		this.addObserver(app.getPrefPres());
		createLogFile();
		client = new Client(socket);
		client.addObserver(this);
		client.start();
	}
	
	public void update(Observable o, Object arg) {
		if (o instanceof Client) {
			if (arg instanceof String) {
				String msg = (String) arg;
				msg = decrypt(msg);
				printMessage(msg);
				logText();
			}
		}
	}

	private void sendOutMsg(String msg) {
		if (!msg.equals("")) {
			 msg = encrypt(msg);
			client.getOut().append(msg + "\n");
			client.getOut().flush();
			setBackTextField();
		}
	}

	private void createLogFile() {
	}
	
	private void setThisViewPresenter(Object view) {
	}

	private void logText() {
	}
	
	private String decrypt(String toDecrypt) {
		return toDecrypt;
	}
	
	private String encrypt(String toEncrypt) {
		return toEncrypt;
	}
	
	private void printMessage(String msg) {	
	}
	

	public void actionPerformed(ActionEvent e) {
		handleGUI(e);
	}

	private void setBackTextField() {
	}

	public void keyPressed(KeyEvent e) {}

	public void keyReleased(KeyEvent e) {}

	public void keyTyped(KeyEvent e) {
	}
}
