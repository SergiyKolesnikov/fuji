package client;

public class Gui{
	
	private String _userNameParam = "setusername";
	
	private boolean feat_UserName_UserName(Client client, String line) {
		
		if (line.toLowerCase().startsWith(_userNameParam))
		{
			String name = line.substring(_userNameParam.length() + 1);
			client.setName(name);
			client.fireAddLine("Username set to " + name);
			return true;
		}
		if (line.toLowerCase().startsWith("-" + _userNameParam))
		{
			String name = line.substring(_userNameParam.length() + 2);
			client.setName(name);
			client.fireAddLine("Username set to " + name);
			return true;
		}
		return false;
	}	
}
