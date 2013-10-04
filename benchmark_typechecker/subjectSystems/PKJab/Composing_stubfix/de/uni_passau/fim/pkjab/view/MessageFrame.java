package de.uni_passau.fim.pkjab.view;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
import java.awt.BorderLayout;
import java.lang.String;
import java.awt.Container;
import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JLabel;
import de.uni_passau.fim.pkjab.util.ChatState;
import de.uni_passau.fim.pkjab.model.Contact;
import de.uni_passau.fim.pkjab.util.listener.ContactComposingEvent;
public class MessageFrame extends javax.swing.JFrame implements de.uni_passau.fim.pkjab.util.listener.ContactListener {
    @Stub
    public void contactTyping(de.uni_passau.fim.pkjab.util.listener.ContactComposingEvent e) {
        return ;
    }
    @Stub
    public javax.swing.JPanel bottomPanel;
    @Stub
    public de.uni_passau.fim.pkjab.model.Contact contact;
}
