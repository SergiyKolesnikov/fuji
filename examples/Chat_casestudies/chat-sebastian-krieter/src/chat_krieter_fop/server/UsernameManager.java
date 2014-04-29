package server; 

import java.util.HashSet; 

public abstract   class  UsernameManager {
	

	private static final String prefix = "Guest";

	

	private static int lastUserGuestID = -1;

	
	private static final HashSet<String> usernames = new HashSet<String>();

	
	
	public static String allocate() {
		String username;
		do {
			username = prefix + (++lastUserGuestID);
		} while(usernames.contains(username));
		
		usernames.add(username);
		return username;
	}

	
	
	public static void remove(String username) {
		usernames.remove(username);
	}

	
	
	public static boolean isAllocated(String username) {
		return usernames.contains(username);
	}

	

	public static boolean allocate(String username) {
		if (usernames.contains(username)) {
			return false;
		} else {
			usernames.add(username);
			return true;
		}
	}


}
