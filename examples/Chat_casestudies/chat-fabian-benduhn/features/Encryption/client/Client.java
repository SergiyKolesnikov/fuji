package client;

import java.io.IOException;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.text.BadLocationException;


import common.TextMessage;
import common.encryption.*;
import common.RefinementException;

/**
 * simple chat client
 */
public class Client implements Runnable {
	private Encrypter encrypter;



	protected Thread thread;
	private void ClientConstructor(String host, int port){
	original(host,port);
	
	encrypter= getEncrypter();
	
	}
	

	private Encrypter getEncrypter()throws RefinementException{
		throw new RefinementException("This method must be refined.");
	
	}
	
	 private void handleLine(String line, boolean encrypted, Object msg)throws Exception{
			
			if(encrypted)line=encrypter.decrypt(line);
			original(line,encrypted);
	
	 }
	 


	public void send(String line) {

		line = encrypter.encrypt(line);
		send(new TextMessage(line,true));
	}

}
