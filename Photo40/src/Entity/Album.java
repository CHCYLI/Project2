package Entity;
import java.util.ArrayList;

public class Album {
	//public final static ArrayList<Album> AlbumList = new ArrayList<>();
	private String albumName;
	private ArrayList<Photo> albumPhoto;
	
	public Album(String name) {
		this.albumName = name;
		this.albumPhoto = new ArrayList<Photo>();
	}
	
	public void setName(String name) {
		this.albumName = name;
	}
	
	public String getAlbumName() {
		return albumName;
	}
	
	public void addPhoto(Photo importedPhoto) {
		albumPhoto.add(importedPhoto);
	}
	
	public boolean deletePhoto(Photo targetPhoto) {
		for (int i = 0; i < albumPhoto.size(); i++) {
			if (targetPhoto.getPath() == albumPhoto.get(i).getPath()) {
				albumPhoto.remove(i);
				return true;
			}
		}
		return false;
	}
	
	
}
