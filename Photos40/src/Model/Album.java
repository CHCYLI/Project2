package Model;
import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable {
	/**
	 * 
	 */
	//public static final long serialVersionUID = 1L;
	private String albumName;
	private ArrayList<Photo> albumPhoto;
	
	/*
	 * @param name name of the album
	 */
	public Album(String name) {
		this.albumName = name;
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
	public boolean deletePhoto(Photo targetPhoto) {
		for (int i = 0; i < albumPhoto.size(); i++) {
			if (targetPhoto.getNamePhoto().equals(albumPhoto.get(i).getNamePhoto())) {
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
