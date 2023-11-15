package Entity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class User {
	//public final static ArrayList<User> UserList = new ArrayList<>();
	private String username;
	private ArrayList<Album> albums;
	
	
	/*
	 * @param name name of user
	 */
	public User (String name) {
		this.username = name;
		this.albums = new ArrayList<Album>();
	}
	
	/*
	 * @return return the name of user
	 */
	public String getUsername() {
		return username;
	}
	
	/*
	 * @param albumName name of album
	 * @return return if valid or not
	 */
	public boolean createAlbum(String albumName) {
		for (int i = 0; i < albums.size(); i++) {
			if (albumName.equals(albums.get(i).getAlbumName())) return false;
		}
		
		albums.add(new Album(albumName));
		return true;
	}
	
	/*
	 * @param albumName name of album
	 * @return return if valid or not
	 */
	public boolean deleteAlbum(String albumName) {
		for (int i = 0; i < albums.size(); i++) {
			if (albumName.equals(albums.get(i).getAlbumName())) {
				albums.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/*
	 * @param oldName name of old album
	 * @param newName name of new album
	 * @return return if valid or not
	 */
	public boolean renameAlbum(String oldName, String newName) {
		if (oldName == newName) return true;
		for (int i = 0; i < albums.size(); i++) {
			if (oldName.equals(albums.get(i).getAlbumName())) {
				albums.get(i).setName(newName);
				return true;
			}
		}
		return false;
	}
	
	/*
	 * @param albumName name of album
	 * @param photo photo itself
	 * @return return if valid or not
	 */
	public boolean addPhoto(String albumName, Photo photo) {
		for (int i = 0; i < albums.size(); i++) {
			if (albumName.equals(albums.get(i).getAlbumName())) {
				if (!(albums.get(i).addPhoto(photo)))
				return false;
			}
		}
		return true;
	}
	
	/*
	 * @param albumName name of album
	 * @param photo photo itself
	 * @return return if valid or not
	 */
	public boolean deletePhoto(String albumName, Photo photo) {
		for (int i = 0; i < albums.size(); i++) {
			if (albumName.equals(albums.get(i).getAlbumName())) {
				if (!(albums.get(i).deletePhoto(photo))) {
					return false;
				} else {
					return true;
				}
			}
		}
		return false;
	}
	
	
	/*
	 * @param albumName name of album
	 * @param photo photo itself
	 * @param caption caption of photo
	 */
	public void setupCaption (String albumName, Photo photo, String caption) {
		for (int i = 0; i < albums.size(); i++) {
			if (albums.get(i).getAlbumName().equals(albumName)) {
				for (int j = 0; j < albums.get(i).getAlbumSize(); j++) {
					if (albums.get(i).getPhoto(photo.getPath()).equals(photo)) {
						albums.get(i).getPhoto(photo.getPath()).setCaption(caption);
						return;
					}
				}
			}
		}
	}
	
	/*
	 * @param albumName name of album
	 * @param photo photo itself
	 * @param tagName the key of tag
	 * @param tagValue the value of tag
	 * @return if can add tag or not
	 */
	public boolean addTag (String albumName, Photo photo, String tagName, String tagValue) {
		for (int i = 0; i < albums.size(); i++) {
			if (albums.get(i).getAlbumName().equals(albumName)) {
				for (int j = 0; j < albums.get(i).getAlbumSize(); j++) {
					if (albums.get(i).getPhoto(photo.getPath()).equals(photo)) {
						albums.get(i).getPhoto(photo.getPath()).addTag(tagName, tagValue);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/*
	 * @param albumName name of album
	 * @param photo photo itself
	 * @param tagName the key of tag
	 * @param tagValue the value of tag
	 */
	public void deleteTag (String albumName, Photo photo, String tagName, String tagValue) {
		for (int i = 0; i < albums.size(); i++) {
			if (albums.get(i).getAlbumName().equals(albumName)) {
				for (int j = 0; j < albums.get(i).getAlbumSize(); j++) {
					if (albums.get(i).getPhoto(photo.getPath()).equals(photo)) {
						albums.get(i).getPhoto(photo.getPath()).deleteTag(tagName, tagValue);
						return;
					}
				}
			}
		}
	}
	
	
	/*
	 * @param original original name of album
	 * @param target the targeted album
	 * @param path path of photo in the system
	 * @return if you can copy the photo or not
	 */
	public boolean copyPhoto (String original, String target, String path) {
		String originalAlbumS = "";
		String targetAlbumS = "";
		Album originalAlbum = new Album(original);
		int originalIndex = -1;
		int targetIndex = -1;
		for (int i = 0; i < albums.size(); i++) {
			if (albums.get(i).getAlbumName().equals(original)) {
				originalAlbumS = albums.get(i).getAlbumName();
				originalAlbum = albums.get(i);
				originalIndex = i;
			}
			
			if (albums.get(i).getAlbumName().equals(target)) {
				targetAlbumS = albums.get(i).getAlbumName();
				//targetAlbum = albums.get(i);
				targetIndex = i;
			}
		}
		
		if (originalAlbumS.equals("")|| targetAlbumS.equals("")) return false;
		/**************************/
		
		if (!originalAlbum.hasPhoto(path)) return false;
		
		Photo tempPhoto = albums.get(originalIndex).getPhoto(path);
		albums.get(targetIndex).addPhoto(tempPhoto);
		
		return true;
	}
	
	
	/*
	 * @param original original name of album
	 * @param target the targeted album
	 * @param path path of photo in the system
	 * @return if you can move the photo or not
	 */
	public boolean movePhoto (String original, String target, String path) {
		String originalAlbumS = "";
		String targetAlbumS = "";
		Album originalAlbum = new Album(original);
		int originalIndex = -1;
		int targetIndex = -1;
		for (int i = 0; i < albums.size(); i++) {
			if (albums.get(i).getAlbumName().equals(original)) {
				originalAlbumS = albums.get(i).getAlbumName();
				originalAlbum = albums.get(i);
				originalIndex = i;
			}
			
			if (albums.get(i).getAlbumName().equals(target)) {
				targetAlbumS = albums.get(i).getAlbumName();
				//targetAlbum = albums.get(i);
				targetIndex = i;
			}
		}
		
		if (originalAlbumS.equals("")|| targetAlbumS.equals("")) return false;
		/**************************/
		
		if (!originalAlbum.hasPhoto(path)) return false;
		
		Photo tempPhoto = albums.get(originalIndex).getPhoto(path);
		albums.get(targetIndex).addPhoto(tempPhoto);
		albums.get(originalIndex).deletePhoto(tempPhoto);
		
		return true;
	}
	
	/*
	 * @param fromDate from which date
	 * @param toDate to which date
	 * @return selected photos
	 * @throws if the input is not a valid date
	 */
	public ObservableList<Photo> searchByCal(String fromDate, String toDate) throws ParseException{ //search by date
		Calendar fromCal = Calendar.getInstance();
		Calendar toCal = Calendar.getInstance();
		ArrayList<Photo> photoList = new ArrayList<Photo>();
		
		SimpleDateFormat tempFrom = new SimpleDateFormat("MM/dd/yyyy");
		Date tempDate = tempFrom.parse(fromDate);
		fromCal.setTime(tempDate);
		
		SimpleDateFormat tempTo = new SimpleDateFormat("MM/dd/yyyy");
		tempDate = tempTo.parse(toDate);
		toCal.setTime(tempDate);
		
		for (int i = 0; i < albums.size(); i++) {
			for (int j = 0; j < albums.get(i).getAlbumSize(); j++) {
				if (albums.get(i).getPhoto(j).getCalendar().after(fromCal) && albums.get(i).getPhoto(j).getCalendar().before(toCal)) {
					photoList.add(albums.get(i).getPhoto(j));
				}
			}
		}
		return FXCollections.observableArrayList(photoList);
	}
	
	
	/*
	 * @param tagName name of tag
	 * @param tagValue value of tag
	 * @param type conjunctive or disjunctive or single name
	 * @return return photo list
	 */
	public ObservableList<Photo> searchByTags(String tagName, String tagValue, String type) {
		ArrayList<Photo> photoList = new ArrayList<Photo>();
		if (tagValue == null) { //if single value (name)
			for (int i = 0; i < albums.size(); i++) {
				for (int j = 0; j < albums.get(i).getAlbumSize(); j++) {
					if (albums.get(i).getPhoto(j).hasTagName(tagName))
						photoList.add(albums.get(i).getPhoto(j));
				}
			}
			
		}
	
		
		if (!(tagValue == null) && !(tagName == null) && type == "AND") { //if conjunctive combination
			for (int i = 0; i < albums.size(); i++) {
				for (int j = 0; j < albums.get(i).getAlbumSize(); j++) {
					if (albums.get(i).getPhoto(j).hasTagName(tagName) && albums.get(i).getPhoto(j).hasTagValue(tagName, tagValue))
						photoList.add(albums.get(i).getPhoto(j));
				}
			}
		}
		
		if (!(tagValue == null) && !(tagName == null)) { //if disjunctive combination, can ignore the type "OR"
			for (int i = 0; i < albums.size(); i++) {
				for (int j = 0; j < albums.get(i).getAlbumSize(); j++) {
					if (albums.get(i).getPhoto(j).hasTagName(tagName) || albums.get(i).getPhoto(j).hasTagValue(tagName, tagValue))
						photoList.add(albums.get(i).getPhoto(j));
				}
			}
		}
		
		return FXCollections.observableArrayList(photoList);
	}
}
