package presenter;

public class ViewPresenter extends Observable implements ActionListener,
		KeyListener, Observer {

	public void update(Observable o, Object arg) {
		original(o, arg);
		if (o instanceof view.Console) {
			sendOutMsg((String)arg);
		}
	}
	
	private void printMessage(String msg) {
		original(msg);
		System.out.println(msg);
	}

}
