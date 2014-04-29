package ui;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPasswordField;

import java.awt.event.ActionListener;
import java.awt.Point;





public class ConnectDialog extends JDialog {
    /**
     * Serial.
     */
    private static final long serialVersionUID = 1L;
    private JPanel buttonPane;
    private JButton loginButton;
    private JButton cancelButton;
    private JTextField usernameField;
    private JPasswordField passwordField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            ConnectDialog dialog = new ConnectDialog();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public ConnectDialog() {
        setTitle("Please Login!");
        setResizable(false);
        setLocation(new Point(15, 15));
        setBounds(100, 100, 248, 170);
        {
            buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            {
                loginButton = new JButton("Login");
                buttonPane.add(loginButton);
                getRootPane().setDefaultButton(loginButton);
            }
            {
                cancelButton = new JButton("Cancel");
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }
        
        JPanel panel = new JPanel();
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panel, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonPane, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(buttonPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(7))
        );
        
        JLabel lblUsername = new JLabel("Username:");
        
        JLabel lblPassword = new JLabel("Password:");
        
        usernameField = new JTextField();
        usernameField.setColumns(10);
        
        passwordField = new JPasswordField();
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
            gl_panel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel.createSequentialGroup()
                    .addGap(19)
                    .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblUsername)
                        .addComponent(lblPassword))
                    .addPreferredGap(ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                    .addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
                        .addComponent(passwordField)
                        .addComponent(usernameField, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap())
        );
        gl_panel.setVerticalGroup(
            gl_panel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel.createSequentialGroup()
                    .addGap(16)
                    .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblUsername))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblPassword)
                        .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(20, Short.MAX_VALUE))
        );
        panel.setLayout(gl_panel);
        getContentPane().setLayout(groupLayout);
    }
    /**
     * Fuegt den Buttons des Dialogs Listener hinzu.
     * Beide Buttons erhalten den selben Listener.
     * @param listener ButtonListener
     */
    public void addButtonListener(ActionListener listener) {
        loginButton.addActionListener(listener);
        cancelButton.addActionListener(listener);
    }
    
    /**
     * Gibt den eingegebenen Username zurueck.
     * @return Username
     */
    public String getUsername() {
        return usernameField.getText();
    }
    
    /**
     * Gibt das eingegebene Passwort zurueck.
     * @return Passwort
     */
    public char[] getPassword() {
        return passwordField.getPassword();
    }
    
    /**
     * Loescht das Passwort aus dem Eingabefeld.
     */
    public void clearPw() {
        passwordField.setText("");
    }
}