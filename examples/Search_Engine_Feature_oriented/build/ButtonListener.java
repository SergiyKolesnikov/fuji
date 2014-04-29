package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.ScrollPane;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;



/**
	 * Event-Listener
	 * 
	 * Die Klasse Listener behandelt alle Events die durch Buttons ausgelÃ¶st
	 * werden
	 * 
	 */
	abstract class ButtonListener$$GUI implements ActionListener {
		Component parents;// = null;
		MainFrame frame;

		public ButtonListener$$GUI(Component parents) {
			this.parents = parents;
			frame = (MainFrame) parents;
		}

		
		public void actionPerformed(ActionEvent e) {
			
			/**
			 * Der startButton startet die Suche. Hierbei kann je nach
			 * Einstellung nach der angegebenen Query gesucht werden, wie auch
			 * nach den grÃ¶Ãsten Dateien oder auch nach dem Zeitpunkt der
			 * letzten Ãnderung.
			 */
			if (e.getSource().equals(frame.startButton)) {
				frame.searchResultContainer.removeAll();
				frame.searchResultContainer.repaint();
				
				// setzt die GrÃ¶Ãe des Panels wieder zum Standard
				frame.searchResultContainer.setPreferredSize(new Dimension(0, 0));

				frame.scrollPane.repaint();

				if (frame.queryTextFieldJComboBox.getSelectedItem()!= null) {
					try {
						frame.mrPinkMain.searchInIndex(((String)frame.queryTextFieldJComboBox.getSelectedItem()), frame.optionStorage.getMaxResults(), frame.optionStorage.getSearchMode());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}else{
					System.out.println("bitte Querry eingeben");
				}	
			}
		}
	}



public class ButtonListener extends  ButtonListener$$GUI  {

	public void actionPerformed(ActionEvent e) {
		/**
		 * Wenn der addButton betÃ¤tigt wird, wird ein FileChooser
		 * geÃ¶ffnet, in dem dann ein Pfad, der indiziert werden soll,
		 * selektiert werden kann.
		 */
		if (e.getSource().equals(frame.addButton)) {
			addingPath();
				
		}
		
		/**
		 * Wenn der removeButton betÃ¤tigt wird, wird das aktuell 
         * selektierte Element entfernt.
		 */
		if (e.getSource().equals(frame.removeButton)) {
			frame.indexPath.removeItem(frame.indexPath.getSelectedItem());
		}
		
		/**
		 * Wenn der indexButton betÃ¤tigt wird, werden alle Pfad
		 * neu indiziert.
		 */
		if (e.getSource().equals(frame.indexButton)) {
			frame.showIndexCreateMessageDialog();
		}
		super.actionPerformed(e);	
			
	}
	
	public void addingPath(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(parents)) {
			String path = fileChooser.getSelectedFile().getPath();
			addPathToGui(path);
			pathAdded(path);
		}
	}
	
	public void pathAdded(String path){
		
	}
	
	public void addPathToGui(String path){
		frame.indexPath.addItem(path);
	}
      // inherited constructors



		public ButtonListener ( Component parents ) { super(parents); }
	
	
}