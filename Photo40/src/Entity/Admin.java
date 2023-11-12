package Entity;

import java.util.ArrayList;

public class Admin {
	private Admin() {}
	private ArrayList<User> usersList;
	
	
	
	public boolean createUser(String username) {
		for (int i = 0; i < usersList.size(); i++) {
			if (username == usersList.get(i).getUsername()) return false;
		}
		
		usersList.add(new User(username));
		return true;
	}
	
	
	public boolean deleteUser(String username) {
		for (int i = 0; i < usersList.size(); i++) {
			if (username == usersList.get(i).getUsername()) {
				usersList.remove(i);
				return true;
			}
		}
		return false;
	}
	
	
}
