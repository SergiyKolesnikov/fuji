package Everything;import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;



/**
 * @author Marcel Jaeschke
 * @since 1.6
 */
public class FormPanel extends JPanel {
	/**
	 * The serial number.
	 */
	private static final long serialVersionUID = 5025344593536488434L;

	/**
	 * Set the test of an label in a headline style.
	 * 
	 * @param text The text of the label.
	 * @param label The label which will be labeled.
	 */
	public static void setHeadlineText ( final String text, final JLabel label ) {
		label.setText( String.format( "<html><body><b>%s</b></body></html>", text ) );
	}
	/**
	 * Set the test of an label in a form style.
	 * 
	 * @param text The text of the label.
	 * @param label The label which will be labeled.
	 */
	public static void setLabelText ( final String text, final JLabel label ) {
		label.setText( String.format( "%s:", text ) );
	}

	/**
	 * The constraints which is used by the layout-manager.
	 */
	private final GridBagConstraints constraints = new GridBagConstraints( GridBagConstraints.RELATIVE, 0, 2, 1, 1D, 0D, GridBagConstraints.NORTHEAST, GridBagConstraints.BOTH, new Insets( 2, 2, 2, 2 ), 1, 1 );

	/**
	 * The default constructor.
	 */
	public FormPanel () {
		super( new GridBagLayout() );
	}
	/**
	 * Add an element.
	 * 
	 * @param component The component which will be added.
	 */
	public void addElement ( final JComponent component ) {
		((FormPanel) this).constraints.gridwidth = 2;
		add( component, ((FormPanel) this).constraints );
		((FormPanel) this).constraints.gridwidth = 1;
		((FormPanel) this).constraints.gridy++;
	}
	/**
	 * Add an element and set a link between both components.
	 * 
	 * @param label The label which discriped the component
	 * @param component The component which will be added.
	 */
	public void addElement ( final JLabel label, final JComponent component ) {
		label.setLabelFor( component );
		add( label, ((FormPanel) this).constraints );
		add( component, ((FormPanel) this).constraints );
		((FormPanel) this).constraints.gridy++;
	}
	/**
	 * Add a ending component to the form.
	 */
	public void addEnd () {
		((FormPanel) this).constraints.weighty = 1D;
		add( Box.createVerticalGlue(), ((FormPanel) this).constraints );
	}
	/**
	 * Add a headline to the form.
	 * 
	 * @param headlineLabel The headline label.
	 */
	public void addHeadline ( final JLabel headlineLabel ) {
		((FormPanel) this).constraints.gridwidth = 2;
		((FormPanel) this).constraints.insets.left = 2;
		add( headlineLabel, ((FormPanel) this).constraints );
		((FormPanel) this).constraints.gridwidth = 1;
		((FormPanel) this).constraints.gridy++;
		((FormPanel) this).constraints.insets.left = 12;
	}
	/**
	 * Add a seperator to the form.
	 */
	public void addSeperator () {
		((FormPanel) this).constraints.gridwidth = 2;
		((FormPanel) this).constraints.insets.left = 2;
		add( new JSeparator(), ((FormPanel) this).constraints );
		((FormPanel) this).constraints.gridwidth = 1;
		((FormPanel) this).constraints.gridy++;
		((FormPanel) this).constraints.insets.left = 12;
	}
}