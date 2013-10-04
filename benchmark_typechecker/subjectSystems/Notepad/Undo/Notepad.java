
import javax.swing.undo.*;

class Notepad {
	//for using undo & redo
	UndoManager undo = new UndoManager();
	UndoAction undoAction = new UndoAction(this);
	RedoAction redoAction = new RedoAction(this);
	Notepad() {
		getTextComponent().getDocument().addUndoableEditListener(new UndoableEditListener(){
			public void undoableEditHappened(UndoableEditEvent e){
				//Remember the edit and update the menus
				undo.addEdit(e.getEdit());
				undoAction.update();
				redoAction.update();
			}
		});
		
		JToolBar toolBar2 = buildToolBar();
		if (toolBar2.getComponentCount() > 0)
		getContentPane().add("North", toolBar2);
		setJMenuBar(buildMenuBar());
	}
	protected JMenu buildEditMenu() {
		JMenu editMenu   = (JMenu) original();
		if (editMenu.getItemCount() > 0) editMenu.addSeparator();
		editMenu.add(undoAction);
		editMenu.add(redoAction);
		return editMenu;
	}
	protected JToolBar buildToolBar() {
		JToolBar toolBar = (JToolBar) original();
		if (toolBar.getComponentCount() > 0) toolBar.addSeparator();
		toolBar.add(undoAction);
		toolBar.add(redoAction);
		return toolBar;
	}
}
