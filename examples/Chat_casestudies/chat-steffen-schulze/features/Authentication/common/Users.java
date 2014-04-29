package common;

public class Users {

	public static boolean checkLogin(String username, String password)
	{
		if (!users.containsKey(username)) return false;
		
		User user = users.get(username);
		return user.getPassword().equals(password);
	}
	
}
