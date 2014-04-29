package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.GroupLayout;
import common.*;

/**
 * simple swing gui for the chat client
 */
public class Gui {

	private JComboBox _encryptionList = null;;

	private JComboBox feat_Encryption_getEncryptionChoiseComboBox() {
		if (_encryptionList == null) {
			_encryptionList = new JComboBox();
			for(Encryption enc : Encryption.values()) {
				_encryptionList.addItem(enc);
			}
		}
		return _encryptionList;
	}
	
	private ITextMessage feat_Encryption_getEncryptedMessage(ITextMessage msg) { 

		switch ((Encryption)feat_Encryption_getEncryptionChoiseComboBox().getSelectedItem()) {
			case ROT13:
				return feat_ROT13_getROT13EncryptedMessage(msg);
			case SwapFirstCharacters:
				return feat_Swapped_getSwappedEncryptedMessage(msg);	
			default: 
				return msg;
		}
	}
	
	private ITextMessage feat_ROT13_getROT13EncryptedMessage(ITextMessage msg)	{ return msg; }
	private ITextMessage feat_Swapped_getSwappedEncryptedMessage(ITextMessage msg) { return msg; }
}
