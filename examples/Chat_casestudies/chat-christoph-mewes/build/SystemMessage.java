

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;



/**
 * serializable message that can be send over the sockets between client and
 * server.
 */
public class SystemMessage extends TextMessage {
	private static final long serialVersionUID = -9161595018411902080L;

	public SystemMessage(String content) {
		super(content);
	}

	public void render(JTextPane pane) {
		StyleContext sc   = StyleContext.getDefaultStyleContext();
		AttributeSet aset = SimpleAttributeSet.EMPTY;

		aset = sc.addAttribute(aset, StyleConstants.Foreground, Color.RED);
		aset = sc.addAttribute(aset, StyleConstants.Bold, Boolean.TRUE);

		pane.setEditable(true);

		int len = pane.getDocument().getLength();
		pane.setCaretPosition(len);
		pane.setCharacterAttributes(aset, false);
		pane.replaceSelection("// " + ((SystemMessage) this).content + "\n");

		aset = SimpleAttributeSet.EMPTY;
		aset = sc.addAttribute(aset, StyleConstants.Foreground, Color.BLACK);
		aset = sc.addAttribute(aset, StyleConstants.Bold, Boolean.FALSE);
		pane.setCharacterAttributes(aset, false);

		pane.setEditable(false);
	}
}