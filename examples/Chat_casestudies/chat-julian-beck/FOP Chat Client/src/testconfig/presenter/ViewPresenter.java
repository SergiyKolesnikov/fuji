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

import model.Crypto; 
import view.GUI; 

import java.awt.Color; 

import java.io.File; 
import java.io.FileWriter; 
import java.io.IOException; 

import javax.swing.JOptionPane; 

public   class  ViewPresenter  extends Observable  implements ActionListener, KeyListener, Observer {
	
	
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

	
	
	private void createLogFile  () {
		File testfile = new File("");
		String path = testfile.getAbsolutePath();
		testfile.delete();
		this.file = new File(path + "\\last conversation.txt");
	}

	

	 public void setThisViewPresenter  (Object view) {
		this.view = (GUI)view;
	}

	
	
	private void logText  () {
		String logText = createLogText(view.getChatWindow().getText());
		writeLog(logText);
	}

	
	
	private String decrypt  (String toDecrypt) {
		return crypt.decrypt(toDecrypt);
	}

	
	
	private String encrypt  (String toEncrypt) {
		 return crypt.encrypt(toEncrypt);
	}

	
	
	 private void  printMessage__wrappee__ascii  (String msg) {	
	}

	

	 private void  printMessage__wrappee__Farbe  (String msg) {
		printMessage__wrappee__ascii(msg);
		view.getChatWindow().append(msg+"\n");
		view.getInputField().setText("");

	}

	
	
	private void printMessage(String msg) {
		if(!msgOK(msg)) {
			msg = msg.split(" ")[0] + " *unterdr�ckt*";
		}
		printMessage__wrappee__Farbe(msg);
	}

	
	

	public void actionPerformed(ActionEvent e) {
		handleGUI(e);
	}

	
	
	private void setBackTextField  () {
		view.getInputField().setText("");
	}

	

	public void keyPressed(KeyEvent e) {}

	

	public void keyReleased(KeyEvent e) {}

	
	
	public void keyTyped  (KeyEvent e) {
		if (e.getKeyChar() == '\n') {
			sendOutMsg(view.getInputField().getText());
		}
	}

	
	
	private  Crypto crypt = new Crypto();

	

	private GUI view;

	
	
	 private void  handleGUI__wrappee__GUI  (ActionEvent e) {
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

	

	private void handleGUI(ActionEvent e) {
		handleColor(e);
		handleGUI__wrappee__GUI(e);
	}

	
	
	private void handleColor  (ActionEvent e) {
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

	

	private String[] evilWords = {"fuck", "fucking", "fucking\n", "fuck\n"};

	
	
	private boolean msgOK(String msg) {
		String[] message = msg.split(" ");
		boolean result = true;
		
		for (int i = 0; i< message.length; i++) {
			if (isEvil(message[i])) {
				result = false;
				break;
			}
		}
		return result;
	}

	

	private boolean isEvil(String toLookUp) {
		boolean result = false;
		
		for(int j = 0; j < evilWords.length; j++) {
			if(toLookUp.equals(evilWords[j])) result = true;
		}
		
		return result;
	}

	
	
	private File file;

	
	
	private String createLogText(String text) {
		String result = "";
		int len = text.length();
		char[] array = new char[len];
		text.getChars(0, len, array, 0);
		int i = 1;
		while (i < len) {
			if (array[i] == '/') {
				result = result + "\r\n";
			} else {
				result = result + array[i];
			}
			i++;
		}
		return result;
	}

	
	
	private void writeLog(String message) {
		try {
			FileWriter out = new FileWriter(file);
			out.append(message);
			out.close();
		} catch (IOException ioExcept) {
			JOptionPane.showMessageDialog(view.getFrame(),
					"Could not write file.", "I/O error",
					JOptionPane.ERROR_MESSAGE);
		}
	}


}
