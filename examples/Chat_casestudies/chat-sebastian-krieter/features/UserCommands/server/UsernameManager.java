package server;

public abstract class UsernameManager {

	public static boolean allocate(String username) {
		if (usernames.contains(username)) {
			return false;
		} else {
			usernames.add(username);
			return true;
		}
	}
	
}
