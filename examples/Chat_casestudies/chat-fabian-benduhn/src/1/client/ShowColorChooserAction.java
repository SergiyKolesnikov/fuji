package client; 

import java.awt.event.ActionEvent; 

import javax.swing.*; 

// This action creates and shows a modeless color chooser dialog.
public  class  ShowColorChooserAction  extends AbstractAction {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	JColorChooser chooser;

	
    JDialog dialog;

	

    ShowColorChooserAction(JFrame frame, JColorChooser chooser) {
        super("Color Chooser...");
        this.chooser = chooser;

        // Choose whether dialog is modal or modeless
        boolean modal = false;

        // Create the dialog that contains the chooser
        dialog = JColorChooser.createDialog(frame, "Dialog Title", modal,
            chooser, null, null);
    }

	

    public void actionPerformed(ActionEvent evt) {
        // Show dialog
        dialog.setVisible(true);

       }


};
