package Model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Admin {
	private ArrayList<User> usersList = new ArrayList<User>();
	private String adminName;
	
	/*
	 * @param name name of the admin
	 */
	public Admin() {
		this.adminName ="admin";
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
		if (username.equals("admin")) return false;
		for (int i = 0; i < usersList.size(); i++) {
			if (username.equals(usersList.get(i).getUsername())) return false;
		}
		
		usersList.add(new User(username));
		return true;
	}
	
	/*
	 * @param username delete user
	 */
	public void deleteUser(int selectedIndex) {
		usersList.remove(selectedIndex);
	}
	
	/*
	 * @param username obtain username
	 */
	public User getUser(String username) {
		for (int i = 0; i < usersList.size(); i++) {
			if (username.equals(usersList.get(i).getUsername())) {
				return usersList.get(i);
			}
		}
		return null;
	}
	
	public String getUsername(String username) {
		for (int i = 0; i < usersList.size(); i++) {
			if (username.equals(usersList.get(i).getUsername())) {
				return usersList.get(i).getUsername();
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
	
	public static ObservableList<String> getUsernameListByFile() throws IOException {
		List<String> finalList = new ArrayList<String>();
		FileInputStream file =  new FileInputStream("data/user.txt");
			//System.out.println("Something here");
		
		int ch;
		boolean firstComma = false;
		ArrayList<Character> charArrayList = new ArrayList<Character>();
		
			while ((ch = file.read()) != -1) {
				//System.out.println("-1!");
				if (ch == ',' && firstComma == false) {
					firstComma = true;
					//System.out.println("first one!");
					continue;
				} else if (ch == ','){
					StringBuilder builder = new StringBuilder(charArrayList.size());
					for(Character c: charArrayList) {
				        builder.append(c);
				    }
					finalList.add(builder.toString());
					charArrayList.clear();
					//System.out.println("Hello!");
				} else {
					charArrayList.add((char)ch);
					//System.out.println("Adding!");
				}
			}
		
		
		file.close();
		
		//for the last one
		StringBuilder builder = new StringBuilder(charArrayList.size());
		for(Character c: charArrayList) {
	        builder.append(c);
	    }
		finalList.add(builder.toString());
		charArrayList.clear();
		
		
		return FXCollections.observableList(finalList);
	}
}
