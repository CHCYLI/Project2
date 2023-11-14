package Entity;

import java.util.ArrayList;

public class Admin {
	private Admin() {}
	private ArrayList<User> usersList;
	
	
	/*
	 * Create user
	 * @param username
	 */
	public boolean createUser(String username) {
		for (int i = 0; i < usersList.size(); i++) {
			if (username == usersList.get(i).getUsername()) return false;
		}
		
		usersList.add(new User(username));
		return true;
	}
	
	/*
	 * 
	 * delete user
	 * @param username
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
	 * obtain username
	 * @param username
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
