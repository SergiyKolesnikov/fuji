import javax.swing.JDialog;
import javax.swing.JFrame;

import de.uni_passau.spl.bytecodecomposer.stubs.Stub;

public class SwingDialog extends JDialog {
	@Stub
	public javax.swing.JPanel ContentPane;
	@Stub
	public SwingDialog(JFrame owner, boolean modal) {
		super(owner, modal);
	}
	@Stub
	public SwingDialog(JFrame owner, String AppTitle, boolean modal) {
		super(owner, AppTitle, modal);
	}
}
