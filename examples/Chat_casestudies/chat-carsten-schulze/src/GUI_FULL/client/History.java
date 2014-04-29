package client; 

import common.Message; 
import java.io.FileWriter; 
import java.io.IOException; 

public  class  History  implements ChatPlugin {
	

	private byte type = 0x20;

	
	private String path;

	
	
	/**
	 * The History plugin only supports Linux in the moment
	 * @param path path to historyfile
	 * @param client assoziated client
	 */
	public History (String path, Client client){
		this.path = path;
		
		client.loadPlugin(this);
		System.out.println("Historyplugin loaded.");
	}

	
	
	@Override
	public byte getType() {
		return type;
	}

	

	@Override
	public Message process(Message msg) {
		if ((msg.getStatus()&type)==0)
		try {
			FileWriter logfile = new FileWriter(path, true);
			for (int i=0;i<msg.getText().length();i++){
				logfile.write((byte) msg.getText().charAt(i));
			}
			logfile.write((byte) '\n');
			logfile.close();
		}
		catch(IOException ex)
		{}
		return null;
	}


}
