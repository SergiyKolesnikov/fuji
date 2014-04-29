package common.logging;

import java.io.*;

public class ChatLogger {

	StringBuffer text;
	private String filename;

	public ChatLogger(String filename) {
		this.filename = filename;
		text = new StringBuffer();
	}

	public void addMessage(String message) throws IOException {
		text.append(message);
		saveLog();
	}

	private void saveLog() throws IOException {
		Writer out = new OutputStreamWriter(new FileOutputStream(filename));
		try {
			out.write(text.toString());
		} finally {
			out.close();
		}
	}

}