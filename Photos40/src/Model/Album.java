package Model;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import application.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Album implements Serializable {
	/**
	 * 
	 */
	//public static final long serialVersionUID = 1L;
	private String albumName;
	private String albumOwner;
	private ArrayList<Photo> albumPhoto;
	
	/*
	 * @param name name of the album
	 */
	public Album(String name, String user) {
		this.albumName = name;
		this.albumOwner = user;
		this.albumPhoto = new ArrayList<Photo>();
	}
	
	/*
	 * @param name name of the album
	 */
	public void setName(String name) {
		this.albumName = name;
	}
	
	/*
	 * @return return the name of album
	 */
	public String getAlbumName() {
		return albumName;
	}
	
	public String getUser() {
		return albumOwner;
	}
	
	/*
	 * @param importedPhoto add photo to the album
	 * @return if added succesfully or not
	 */
	public boolean addPhoto(Photo importedPhoto) {
		if (hasPhoto(importedPhoto.getNamePhoto())) {
			return false;
		}
		albumPhoto.add(importedPhoto);
		return true;
	}
	
	/*
	 * @param targetPhoto delete photo from album
	 * @return return if successful or not
	 */
	public boolean deletePhoto(String photoName) {
		for (int i = 0; i < albumPhoto.size(); i++) {
			if (photoName.equals(albumPhoto.get(i).getNamePhoto())) {
				albumPhoto.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/*
	 * @return return the size of albumPhoto length
	 */
	public int getAlbumSize() {
		return albumPhoto.size();
	}
	
	
	/*
	 * @param getNamePhoto getNamePhoto of photo
	 * @return return the photo or null if not found (by getNamePhoto)
	 */
	public Photo getPhoto(String getNamePhoto) {
		for (int i = 0; i < albumPhoto.size(); i++) {
			if (getNamePhoto.equals(albumPhoto.get(i).getNamePhoto())) return albumPhoto.get(i);
		}
		
		return null;
	}
	
	/*
	 * @param index index of arraylist
	 * @return return the photo of that position
	 */
	public Photo getPhoto(int index) { //get photos by index
		return albumPhoto.get(index);
	}
	
	/*
	 * @param name name of photo
	 * @return return the index of photo
	 */
	public int getPhotoIndex(String name) {
		for (int i = 0; i < albumPhoto.size(); i++) {
			if (albumPhoto.get(i).getNamePhoto().equals(name)) return i;
		}
		return -1;
	}
	
	/*
	 * @throws IOException 
	 * @return return photoname list
	 */
	public ObservableList<String> getPhotoNameListByFile() throws IOException {
		//System.out.println("Something here");
		File f = new File("data/"+ albumOwner + albumName+"photo.txt");
		if(!f.exists() && !f.isDirectory()) { 
			FileOutputStream createfile = new FileOutputStream("data/"+ albumOwner +albumName +"photo.txt");
			createfile.close();
		}
		
		List<String> finalList = new ArrayList<String>();
		FileInputStream file =  new FileInputStream("data/"+ albumOwner+albumName +"photo.txt");
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
		
		//Make sure next time running the program can detect the duplicate name
		albumPhoto.clear();
		for (int i = 0; i < finalList.size(); i++) {
			albumPhoto.add(new Photo(finalList.get(i), null, null, null));
		}
		//this photo needs to be finalized
		return FXCollections.observableList(finalList);
	}
	
	/*
	 * @param path path of photo
	 * @return return if the album has the photo or not
	 */
	public boolean hasPhoto(String getNamePhoto) {
		for (int i = 0; i < albumPhoto.size(); i++) {
			if (getNamePhoto.equals(albumPhoto.get(i).getNamePhoto())) return true;
		}
		
		return false;
	}
}