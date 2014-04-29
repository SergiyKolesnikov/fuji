package common;

import common.User;

public class Users {
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
