import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JButton;

public class Main extends JFrame {
	
	private int i = 2;
	private JMenu GraphMenu;
	private GraphPanel panel;
	private JMenuItem jMenuItem_newNode;

	private void doTheLayout() {
		member++;
		JPanel visualPanel = new JPanel();
		visualPanel.setLayout(new GridLayout(0,1,0,0));
		
		panel = new GraphPanel();
		visualPanel.add(panel.getGraphComponent());
		
		this.getContentPane().add(visualPanel);
		original();
	}

	private void doTheMenuBars() {
		original();
		GraphMenu = new JMenu("Graph");
		
		jMenuItem_newNode = new JMenuItem("New Node");
		jMenuItem_newNode.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent evt) {
	        	panel.getGraph().getModel().beginUpdate();
	        	panel.getGraph().insertVertex(panel.getGraph().getDefaultParent(), null, i, 10, 10, 90, 90);
	        	panel.getGraph().getModel().endUpdate();
	    		i++;
	        }
	    });
		
		GraphMenu.add(jMenuItem_newNode);
		
		jMenuBar1.add(GraphMenu);
	}

}