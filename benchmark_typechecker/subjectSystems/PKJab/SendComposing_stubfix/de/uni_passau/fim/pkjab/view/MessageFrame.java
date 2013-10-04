package de.uni_passau.fim.pkjab.view;
import de.uni_passau.spl.bytecodecomposer.stubs.Stub;
import java.lang.String;
import javax.swing.text.JTextComponent;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import de.uni_passau.fim.pkjab.util.ChatState;
import de.uni_passau.fim.pkjab.model.Contact;
public class MessageFrame extends javax.swing.JFrame implements javax.swing.event.DocumentListener, de.uni_passau.fim.pkjab.util.listener.ContactListener {
    @Stub
    public javax.swing.JTextArea composingArea;
    @Stub
    public de.uni_passau.fim.pkjab.model.Contact contact;
}
