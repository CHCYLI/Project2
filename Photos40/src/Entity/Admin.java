package Entity;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Admin {
	private ArrayList<User> usersList;
	private String adminName;
	
	/*
	 * @param name name of the admin
	 */
	private Admin(String name) {
		this.adminName = name;
		usersList = new ArrayList<User>();
	}
	
	/*
	 * @return the name of admin
	 */
	public String getName() {
		return adminName;
	}
	
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
	
	/*
	 * @return return the list of users
	 */
	public ObservableList<String> usernameList() {
		ArrayList<String> finalList = new ArrayList<String>();
		
		for (int i = 0; i < usersList.size(); i++) {
			finalList.add(usersList.get(i).getUsername());
		}
		
		return FXCollections.observableList(finalList);
	}
}
