package gui.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import gui.util.GuiTLH;

import gui.Gui;

/**
 * TODO description
 */
public class ClientPanel {

	private HashMap<String, ImageIcon> images;

	private void init() {
		original();
		images = new HashMap<String, ImageIcon>();
		images.put(":D", GuiTLH.loadImageIcon("smilies/icon_biggrin.gif"));
		images.put(":~", GuiTLH.loadImageIcon("smilies/icon_confused.gif"));
		images.put("^^", GuiTLH.loadImageIcon("smilies/icon_cool.gif"));
		images.put(":(", GuiTLH.loadImageIcon("smilies/icon_cry.gif"));
		images.put(">(", GuiTLH.loadImageIcon("smilies/icon_evil.gif"));
		images.put(":!", GuiTLH.loadImageIcon("smilies/icon_idea.gif"));
		images.put(":|", GuiTLH.loadImageIcon("smilies/icon_neutral.gif"));
		images.put(":?", GuiTLH.loadImageIcon("smilies/icon_question.gif"));
		images.put(":p", GuiTLH.loadImageIcon("smilies/icon_razz.gif"));
		images.put(":*", GuiTLH.loadImageIcon("smilies/icon_redface.gif"));
		images.put(":r", GuiTLH.loadImageIcon("smilies/icon_rolleyes.gif"));
		images.put(":)", GuiTLH.loadImageIcon("smilies/icon_smile.gif"));
		images.put(":o", GuiTLH.loadImageIcon("smilies/icon_surprised.gif"));
		images.put(";)", GuiTLH.loadImageIcon("smilies/icon_wink.gif"));
	}

	private void initChatView() {
		original();
		Document doc = tpShowText.getDocument();
		doc.addDocumentListener(new DocumentListener() {

			private int lastCheck = 0;

			@Override
			public void insertUpdate(DocumentEvent event) {
				final DocumentEvent e = event;
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						if (e.getDocument() instanceof StyledDocument) {
							try {

								StyledDocument doc = (StyledDocument) e
										.getDocument();
								int doclength = doc.getLength();
								String text = doc.getText(lastCheck, doclength
										- lastCheck);

								// Check for :
								checkForMatch(":", text, doc, lastCheck);
								checkForMatch(";", text, doc, lastCheck);
								checkForMatch("^", text, doc, lastCheck);
								checkForMatch(">", text, doc, lastCheck);
								lastCheck = doclength - 1;
							} catch (BadLocationException e1) {
								e1.printStackTrace();
							}
						}
					}

				});
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
			}
		});
	}

	private void modifyExtensionPanel(JPanel panel) {
		JPanel smiliesPanel = new JPanel();
		smiliesPanel.setLayout(new BorderLayout());
		JLabel colorLabel = new JLabel("Smilies:");
		smiliesPanel.add(colorLabel, BorderLayout.NORTH);
		JPanel btPanel = new JPanel();
		btPanel.setLayout(new GridLayout(0, 4));
		for (Entry<String, ImageIcon> e : images.entrySet()) {
			JButton sButton = new JButton(e.getValue());
			sButton.setActionCommand("smi"+e.getKey());
			sButton.addActionListener(this);
			btPanel.add(sButton);
		}
		smiliesPanel.add(btPanel, BorderLayout.CENTER);
		panel.add(smiliesPanel);
		original(panel);
	}
	
	public void actionPerformed(ActionEvent e) {
		String acom = e.getActionCommand();
		if (acom.substring(0,3).equals("smi")) {
			taSendText.append(acom.substring(3, acom.length()));
		}else{
			original(e);
		}
	}
	
	private void checkForMatch(String firstMatcher, String text,
			StyledDocument doc, int offset) throws BadLocationException {
		int i = text.indexOf(firstMatcher);
		// && (i+1) < doclength
		while (i >= 0) {
			ImageIcon icon = getMatchedSmiley(text.substring(i, i + 2));
			if (icon != null) {
				final SimpleAttributeSet attrs = new SimpleAttributeSet(doc
						.getCharacterElement(i).getAttributes());
				if (StyleConstants.getIcon(attrs) == null) {
					StyleConstants.setIcon(attrs, icon);
					doc.remove(offset + i, 2);
					doc.insertString(offset + i, ":)", attrs);
				}
				i = text.indexOf(firstMatcher, i + 2);
			} else {
				i = text.indexOf(firstMatcher, i + 1);
			}
		}
	}

	private ImageIcon getMatchedSmiley(String sCode) {
		sCode = sCode.trim();
		if (sCode.length() < 2) {
			return null;
		}
		return images.get(sCode);
	}
}