package crypt;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import common.IMessage;






/**
 * Stellt Methoden zum Verschluesseln von Streams zur
 * Verfuegung.
 * @author Ralf Beczkowiak
 *
 */
abstract class Crypt$$EncryptionOn$crypt {
    
    public byte[] encode(Object obj, String algorithm, byte[] key) {
        try {
            Cipher c = Cipher.getInstance(algorithm);
            Key k = new SecretKeySpec(key, algorithm);
            c.init( Cipher.ENCRYPT_MODE, k );
            
            // ByteArrayOutputStream zum Zwischenspeichern einrichten
            // Direkt geht nicht, da aufgrund der Verschluesselung
            // nur Teile uebertragen wuerden bis der Stream geschlossen
            // wird
            ByteArrayOutputStream byout = new ByteArrayOutputStream();
            CipherOutputStream cos = new CipherOutputStream(byout, c);
            ObjectOutputStream output = new ObjectOutputStream(cos);
            
            output.writeObject(obj);
            output.close();
            
            return byout.toByteArray();
            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Object decode(byte[] input, String algorithm, byte[] key) {
        
        try {
            Cipher c = Cipher.getInstance(algorithm);
            Key k = new SecretKeySpec(key, algorithm);
            c.init( Cipher.DECRYPT_MODE, k );
            
            ByteArrayInputStream bis = new ByteArrayInputStream(input);
            CipherInputStream cis = new CipherInputStream(bis, c);
            ObjectInputStream ois = new ObjectInputStream(cis);
            
            Object obj = ois.readObject();
            ois.close();
            return obj;
            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Liesst aus einem ObjectInputStream ein Bytearray aus.
     * @param input InputStream
     * @return eingelesenes Bytearray
     * @throws IOException 
     */
    public byte[] readToByteArray(BufferedInputStream input) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] chunk = new byte[4096];
        
        while(input.read(chunk) > 0) {
            bos.write(chunk);
        }
        bos.close();
        
        return bos.toByteArray();
    }
    
    public byte[] encode(Object obj) {
        return null;
    }
    
    public Object decode(byte[] array) {
        return null;
    }
}

abstract class Crypt$$AES$crypt extends  Crypt$$EncryptionOn$crypt  {
	public byte[] encode(Object obj) {
        return encode(obj, "AES", "einszweidreivier".getBytes());
	}

	public Object decode(byte[] array) {
        return decode(array, "AES", "einszweidreivier".getBytes());
    }
}

abstract class Crypt$$DES$crypt extends  Crypt$$AES$crypt  {
	public byte[] encode(Object obj) {
        return encode(obj, "DES", "einszwei".getBytes());
	}
	
	public Object decode(byte[] array) {
        return decode(array, "DES", "einszwei".getBytes());
    }
}

public class Crypt extends  Crypt$$DES$crypt  {
	public byte[] encode(Object obj) {
        byte[] array = null;
        array = encode(obj, "DES", "einszwei".getBytes());
        
        array = encode(array, "AES", "einszweidreivier".getBytes());
        
        return array;
    }
    
    public Object decode(byte[] array) {
        Object obj = null;
        
        obj = decode(array, "AES", "einszweidreivier".getBytes());
        if (obj instanceof byte[]) {
            array = (byte[]) obj;
        } else {
            throw new RuntimeException("Failed to decode Message!");
        }
        
        obj = decode(array, "DES", "einszwei".getBytes());
        return obj;
    }
}