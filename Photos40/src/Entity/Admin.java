package Entity;

import java.util.ArrayList;

public class Admin {
	private Admin() {}
	private ArrayList<User> usersList;
	
	
	/*
	 * @param username Create user
	 */
	public boolean createUser(String username) {
		for (int i = 0; i < usersList.size(); i++) {
			if (username == usersList.get(i).getUsername()) return false;
		}
		
		usersList.add(new User(username));
		return true;
	}
	
	/*
	 * @param username delete user
	 */
	public boolean deleteUser(String username) {
		for (int i = 0; i < usersList.size(); i++) {
			if (username == usersList.get(i).getUsername()) {
				usersList.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/*
	 * @param username obtain username
	 */
	public User getUser(String username) {
		for (int i = 0; i < usersList.size(); i++) {
			if (username == usersList.get(i).getUsername()) {
				return usersList.get(i);
			}
		}
		return null;
	}
	
	
}
