package Base;


import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;



/**
 * Optionsfenster
 * 
 * Zeigt ein Fenster an, das anbietet, alle verfÃ¼gbaren EinstellungsmÃ¶glichkeiten zu verÃ¤ndern.
 * 
 * @author Mr. Pink
 */
public class OptionWindow {
	// Fensterelemente, die fÃ¼r besseren Zugriff global deklariert sind
	private JDialog dialog;
	private JTextField tfMaxHits;
	private JRadioButton rbNormal;
	private JRadioButton rbLargest;
	private JRadioButton rbLastModified;

	// Zwischenspeicher fÃ¼r die Optionen
	private OptionStorage os;

	/**
	 * Oberen Fensterteil generieren
	 * 
	 * Baut den oberen Teil des Fensters, der die AuswahlmÃ¶glichkeiten enthÃ¤lt.
	 */
	private void buildChoices() {
		// Auswahlfelder horizontal ausgerichtet in ein Panel
		JPanel choicesPanel = new JPanel();
		choicesPanel.setLayout(new BoxLayout(choicesPanel, BoxLayout.PAGE_AXIS));
		choicesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel label1 = new JLabel("Max number of hits presented: ");
		choicesPanel.add(label1);
		tfMaxHits = new JTextField(Integer.toString(os.getMaxResults()));
		choicesPanel.add(tfMaxHits);
		choicesPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		// ButtonGroup sorgt dafÃ¼r, dass immer nur 1 RadioButton aktiviert ist
		ButtonGroup bg = new ButtonGroup();
		rbNormal = new JRadioButton("Normal search");
		rbNormal.setSelected(!os.isSearchLargest() && !os.isSearchMostRecent());
		bg.add(rbNormal);
		choicesPanel.add(rbNormal);

		rbLargest = new JRadioButton("Show largest files");
		rbLargest.setSelected(os.isSearchLargest());
		bg.add(rbLargest);
		choicesPanel.add(rbLargest);

		rbLastModified = new JRadioButton("Show last modified files");
		rbLastModified.setSelected(os.isSearchMostRecent());
		bg.add(rbLastModified);
		choicesPanel.add(rbLastModified);

		dialog.add(choicesPanel, BorderLayout.CENTER);
	}

	/**
	 * Unteren Fensteteil generieren
	 * 
	 * Baut den unteren Teil des Fensters und die Buttons "OK" und "Cancel".
	 */
	private void buildButtons() {
		final String OK     = "OK";
		final String CANCEL = "Cancel";

		// eigenen ActionListener definieren, den beide Buttons verwenden
		class Listener implements ActionListener {
			public Listener() {
			}

			
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals(OK)) {
					try {
						int t = Integer.parseInt(tfMaxHits.getText());
						if (t < 1) {
							throw new NumberFormatException();
						}
						os.setMaxResults(t);
						if(rbLargest.isSelected()){
							os.setSearchMode(OptionStorage.SEARCHLARGEST);
						} else if(rbLastModified.isSelected()){
							os.setSearchMode(OptionStorage.SEARCHLASTMODIFIED);
						}else{
							os.setSearchMode(OptionStorage.SEARCHNORMAL);
						}
						
					} catch (NumberFormatException nfe) {
						JOptionPane
								.showMessageDialog(
										dialog,
										"\"Maximum number of hits\" must be a positive integer!",
										"Warning - incorrect parameters",
										JOptionPane.WARNING_MESSAGE);
						return;
					}
				}

				// Dialog schlieÃen
				dialog.dispose();
			}
		}

		JButton okBtn = new JButton(OK);
		okBtn.addActionListener(new Listener());
		JButton cancelBtn = new JButton(CANCEL);
		cancelBtn.addActionListener(new Listener());

		// die 2 Buttons in ein Panel, von links nach rechts
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.LINE_AXIS));
		btnPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		btnPanel.add(Box.createHorizontalGlue());
		btnPanel.add(okBtn);
		btnPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		btnPanel.add(cancelBtn);

		// zu Frame unten hinzufÃ¼gen
		dialog.add(btnPanel, BorderLayout.PAGE_END);
	}

	/**
	 * Konstruktor.
	 * 
	 * Zeigt das Fenster applikationsmodal an.
	 * 
	 * @param os      Objekt, das die Einstellungen kapselt, die bearbeitet werden sollen
	 * @param parent  Ã¼bergeordnetes Fenster
	 */
	public OptionWindow(OptionStorage os, JFrame parents) {
		this.os = os;

		dialog = new JDialog(parents, "Options");
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setSize(300, 200);
		dialog.setLayout(new BorderLayout());
		// Hauptfenster deaktivieren; warten, bis der Benutzer mit den
		// Einstellungen fertig ist
		dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

		buildChoices();
		buildButtons();

		// GrÃ¶Ãe an Komponenten im Fenster anpassen
		dialog.pack();
		dialog.setResizable(false);
		dialog.setVisible(true);
	}

	/**
	 * Fenster statisch aufrufen
	 * 
	 * Zeigt das Einstellungsfenster applikationsmodal an.
	 * 
	 * @param os      Objekt, das die Einstellungen kapselt, die bearbeitet werden sollen
	 * @param parent  Ã¼bergeordnetes Fenster
	 */
	public static void showModal(OptionStorage os, JFrame parents) {
		
		OptionWindow ow = new OptionWindow(os, parents);
	}
}