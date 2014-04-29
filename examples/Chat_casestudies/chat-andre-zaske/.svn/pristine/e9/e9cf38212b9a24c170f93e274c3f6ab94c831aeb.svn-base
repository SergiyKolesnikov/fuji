package network.server.packets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class TransferPacket implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8059328567484990134L;
	
	private transient DataPacket packet = null;

	public TransferPacket(DataPacket dataPacket){
		this.packet = dataPacket;
	}
	
	public DataPacket getPacket(){
		return packet;
	}
	
	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.defaultWriteObject();
		stream.writeObject(serialize());
	}

	private void readObject(ObjectInputStream stream) throws IOException,
			ClassNotFoundException {
		stream.defaultReadObject();
		packet = deserialize((String) stream.readObject());
	}

	private DataPacket deserialize(String s) throws IOException,
			ClassNotFoundException {
		byte[] data = s.getBytes();
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(
				data));
		Object o = ois.readObject();
		ois.close();
		return (DataPacket)o;
	}

	private String serialize() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(this);
		oos.close();
		return new String(baos.toByteArray());
	}

}
