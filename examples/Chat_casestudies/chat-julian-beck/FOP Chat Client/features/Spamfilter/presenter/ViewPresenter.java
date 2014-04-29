package presenter;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

public class ViewPresenter extends Observable implements ActionListener,
KeyListener, Observer {

	private String[] evilWords = {"fuck", "fucking", "fucking\n", "fuck\n"};
	
	private void printMessage(String msg) {
		if(!msgOK(msg)) {
			msg = msg.split(" ")[0] + " *unterdrückt*";
		}
		original(msg);
	}
	
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
}