import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;


public class Edge  {
 
  private JButton play;
  
  public Edge( ) {
  
    play=makeNavigationButton(new ActionListener(){
	    public void actionPerformed(ActionEvent e){

	    }
	}
	);  
  }

    protected JButton makeNavigationButton( ActionListener act ){
	JButton button=new JButton();
	button.addActionListener(act);

	return button;
    }
}

