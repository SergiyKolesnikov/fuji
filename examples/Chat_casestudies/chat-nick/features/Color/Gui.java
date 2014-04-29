import java.util.LinkedList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JComboBox;

public class Gui {
	protected JComboBox colorPicker;
	protected List<Color> availableColors;	
	
	private void initLayout() {

		colorPicker = new JComboBox();
		availableColors = new LinkedList<Color>();
				
		availableColors.add(new Color("Schwarz"));
		availableColors.add(new Color("Grün"));			
		availableColors.add(new Color("Gelb"));
		availableColors.add(new Color("Rot"));
		
		for (Color c: availableColors) {
			colorPicker.addItem(c);
		}
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(outputTextbox)
                    .addComponent(inputField))
                .addGroup(layout.createSequentialGroup()
                	.addComponent(colorPicker))
        );
        
        layout.setVerticalGroup(layout.createSequentialGroup()
                    .addComponent(outputTextbox)
              .addGroup(layout.createParallelGroup()
                    .addComponent(inputField)
                    .addComponent(colorPicker))
        );				
	}	
	
	private void sendNewTextMessage(TextMessage msg) {
		msg.setColor((Color) colorPicker.getSelectedItem());
		original(msg);
	}
}