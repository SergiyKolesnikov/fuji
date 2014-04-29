package common; 

import java.util.HashMap; 
import java.util.Map; 

import common.User; 

public   class  Users {
	

	private static Map<String, User> users = createMap();

	

    private static Map<String, User> createMap() 
    {
    	Map<String, User> result = new HashMap<String, User>();
        User user1 = new User("user1", "qwertz", "blau");
        result.put(user1.getUsername(), user1);
        User user2 = new User("user2", "123456", "gelb");
        result.put(user2.getUsername(), user2);
        User user3 = new User("user3", "qwertz", "gr�n");
        result.put(user3.getUsername(), user3);
        User user4 = new User("user4", "123456", "weiss");
        result.put(user4.getUsername(), user4);
        User user5 = new User("server", "admin", "schwarz");
        result.put(user5.getUsername(), user5);
        return result;
    }

	
	
	public static User getUser(String userName)
	{
		return users.get(userName);
	}

	

	public static boolean checkLogin(String username, String password)
	{
		if (!users.containsKey(username)) return false;
		
		User user = users.get(username);
		return user.getPassword().equals(password);
	}

	
	public static boolean existUsername(String username)
	{
		for (User user : users.values()) {
			if (user.getUsername().equals(username)) return true;
		}
		
		return false;
	}

	
	
	public static void updateUserMap(String oldUsername, String newUsername)
	{
		User user = users.get(oldUsername);
		users.remove(oldUsername);
		users.put(newUsername, user);
	}


}
