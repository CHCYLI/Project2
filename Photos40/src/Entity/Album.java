package Entity;
import java.util.ArrayList;

public class Album {
	//public final static ArrayList<Album> AlbumList = new ArrayList<>();
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
	 */
	public void addPhoto(Photo importedPhoto) {
		albumPhoto.add(importedPhoto);
	}
	
	/*
	 * @param targetPhoto delete photo from album
	 */
	public boolean deletePhoto(Photo targetPhoto) {
		for (int i = 0; i < albumPhoto.size(); i++) {
			if (targetPhoto.getPath() == albumPhoto.get(i).getPath()) {
				albumPhoto.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public int getAlbumSize() {
		return albumPhoto.size();
	}
	
	public Photo getPhoto(String path) {
		for (int i = 0; i < albumPhoto.size(); i++) {
			if (path == albumPhoto.get(i).getPath()) return albumPhoto.get(i);
		}
		
		return null;
	}
	
	public boolean hasPhoto(String path) {
		for (int i = 0; i < albumPhoto.size(); i++) {
			if (path == albumPhoto.get(i).getPath()) return true;
		}
		
		return false;
	}
}
