package client;

import java.awt.Color;
import javax.swing.text.StyleConstants;

public class Gui {

	private void createStyles() {
		original();
		StyleConstants.setForeground(styles[Message.SENDERTYPE_DEFATULT], Color.black);
        StyleConstants.setForeground(styles[Message.SENDERTYPE_SELF], Color.blue);
        StyleConstants.setForeground(styles[Message.SENDERTYPE_OTHER], Color.red);
        StyleConstants.setForeground(styles[Message.SENDERTYPE_SERVER], Color.gray);
	}
}
