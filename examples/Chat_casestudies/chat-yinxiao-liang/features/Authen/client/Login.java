package client;
import java.awt.*;

import java.awt.event.*;
import java.awt.Component;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;
import javax.swing.*;

import client.Client;

import common.ConnectionMessage;
import common.LoginReply;




class Login extends JFrame implements ActionListener{
	
	

Client client;
JPanel jp1=new JPanel();
JPanel jp2=new JPanel();
JPanel jp3=new JPanel();


JButton jb1= new JButton("enter");
JButton jb2=new JButton("exit");


JLabel jl2=new JLabel(" Password");
JLabel jl3=new JLabel("Port:8081 Password:1");
JLabel jl4=new JLabel(" Username");

JTextField jt3 = new JTextField(10);
JPasswordField jt2=new JPasswordField(10);
public String StringPasswords;
public String StringUsername;

Login(String title, Client c)
{
super(title);
setResizable(false); 
setLocation(300,300);
setSize(300,150);
this.client= c;

jp1.add(jl4);
jp1.add(jt3);
jp2.add(jl2);
jp2.add(jt2);
jp3.add(jb1);
jp3.add(jb2);
jp3.add(jl3);
  
setLayout(new BorderLayout());
add(BorderLayout.NORTH,jp1);
add(BorderLayout.CENTER,jp2);
add(BorderLayout.SOUTH,jp3);


jb1.addActionListener(this);
jb2.addActionListener(this);	
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setVisible(true);  

}

public void actionPerformed(ActionEvent e){
if(e.getSource()==jb1){
	char[] passwords=jt2.getPassword();
	StringUsername = jt3.getText();

	StringPasswords = String.valueOf(passwords);

	client.connect();
}
else if(e.getSource()==jb2){

System.exit(0);
}


}

}
  	
