// SwingApp.java - Abstract program that defines the basic "shell"
// of a Swing Application. Specific Swing applications are subclasses
// of SwingApp
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SwingApp extends JFrame {
   @Stub
   public SwingApp() {
      super();
   }
   @Stub
   public SwingApp( String AppTitle ) {
      super( AppTitle );     // set title
   }
}
