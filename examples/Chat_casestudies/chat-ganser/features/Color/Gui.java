import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JComboBox;

public class Gui {
    
    private static final String WHITE = "white";
    private static final String BLACK = "black";
    private static final String BLUE = "blue";
    private static final String GREEN = "green";
    private static final String YELLOW = "yellow";
    private static final String ORANGE = "orange";
    private static final String RED = "red";
    private static final String PINK = "pink";
    private static final String PURPLE = "purple";
    private static final String BROWN = "brown";
    
    private JComboBox chooser;
    
    private void setup(String title, Client chatClient) {
	original(title, chatClient);
	Container oldContentPane = this.getContentPane();
	JPanel newContentPane = new JPanel();
	newContentPane.setLayout(new BoxLayout(newContentPane, BoxLayout.Y_AXIS));
	newContentPane.add(oldContentPane);
	this.chooser = new JComboBox(new String[] { BLACK, BLUE, BROWN, GREEN,
		ORANGE, PINK, PURPLE, RED, YELLOW, WHITE });
	newContentPane.add(chooser);
	this.setContentPane(newContentPane);
	this.pack();
    }
    
    private ActionListener getInput() {
	return new ActionListener() {

	    public void actionPerformed(ActionEvent e) {
		String color = (String)Gui.this.chooser.getSelectedItem();
		chatClient.send((String) inputField.getText(), color);
		inputField.setText("");
	    }
	};
    }
}
