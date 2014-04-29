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

import common.RefinementException;

/**
 * simple chat client
 */
public class Client implements Runnable {
	

	private Encrypter getEncrypter()throws RefinementException{
		return new Rot13Encrypter();
	
	}
	
}
