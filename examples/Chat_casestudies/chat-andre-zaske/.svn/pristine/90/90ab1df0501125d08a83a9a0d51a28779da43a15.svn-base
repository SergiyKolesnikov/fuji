package gui.util;

import java.io.OutputStream;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class JTextAreaOutputStream extends OutputStream {
	private boolean errorStream;
	private JTextPane debugTextPane = null;

	public JTextAreaOutputStream(JTextPane textPane) {
		super();
		errorStream = false;
		debugTextPane = textPane;
	}

	public JTextAreaOutputStream(JTextPane textPane, boolean _errorStream) {
		super();
		errorStream = _errorStream;
		debugTextPane = textPane;
	}

	public void write(int i) {
		char[] chars = new char[1];
		chars[0] = (char) i;
		String s = new String(chars);
		Document doc = debugTextPane.getDocument();
		try {
			if (errorStream) {
				doc.insertString(doc.getLength(), s,
						debugTextPane.getStyle("Red"));
			} else {
				doc.insertString(doc.getLength(), s, null);
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	public void write(char[] buf, int off, int len) {
		String s = new String(buf, off, len);
		Document doc = debugTextPane.getDocument();
		try {
			if (errorStream) {
				doc.insertString(doc.getLength(), s,
						debugTextPane.getStyle("Red"));
			} else {
				doc.insertString(doc.getLength(), s, null);
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
}