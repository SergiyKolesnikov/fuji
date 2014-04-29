package presenter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

public class ViewPresenter extends Observable implements ActionListener,
		KeyListener, Observer {
	
	private File file;
	
	private void createLogFile() {
		File testfile = new File("");
		String path = testfile.getAbsolutePath();
		testfile.delete();
		this.file = new File(path + "\\last conversation.txt");
	}
	
	private void logText() {
		String logText = createLogText(view.getChatWindow().getText());
		writeLog(logText);
	}
	
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
