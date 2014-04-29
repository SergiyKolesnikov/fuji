package common; 

import java.io.Serializable; 

/**
 * TODO description
 */
public   class  ServerCommandMessage  implements Serializable {
	

	private static final long serialVersionUID = -9161554284923902079L;

	
	private String command;

	
	private String options;

	

	public static ServerCommandMessage tryInterpretAsServerCommand(String messageText){
		if(!messageText.substring(0, 1).equals("/"))
			return null;
		String commandAndOptions = messageText.substring(1);
		int spacePosition = messageText.indexOf(" ");
		String command = "";
		String options = "";
				
		if(spacePosition > 0 && spacePosition < commandAndOptions.length()){
			command = commandAndOptions.substring(0, spacePosition-1);
			options = commandAndOptions.substring(spacePosition);
		}else{
			command = commandAndOptions.trim();
		}
		System.out.println("Test:"+command+":"+options+":");
				
		if(isKnownCommand(command, options)){
			return new ServerCommandMessage(command, options);
		}
		return null;
	}

	
	
	private ServerCommandMessage(String command, String options){
		this.command = command;
		this.options = options;
	}

	
	
	public String setOptions(String options){
		this.options = options;
		return options;
	}

	
	
	public String getOptions(){
		return options;
	}

	
	
	public String getCommand(){
		return command;
	}

	
	
	public String setCommand(String command){
		this.command = command;
		return command;
	}

	
	
	 private static boolean  isKnownCommand__wrappee__ServerCommands  (String command, String options) {
		return false;
	}

	
	 private static boolean  isKnownCommand__wrappee__SendUserSpecificMessage  (String command, String options) {
		if(command.equalsIgnoreCase("msg")){
			return true;
		}
		return isKnownCommand__wrappee__ServerCommands(command, options);
	}

	
	private static boolean isKnownCommand(String command, String options) {
		if(command.equalsIgnoreCase("username")){
			return true;
		}
		return isKnownCommand__wrappee__SendUserSpecificMessage(command, options);
	}


}
