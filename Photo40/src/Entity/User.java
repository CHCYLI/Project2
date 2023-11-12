package Entity;
import java.util.ArrayList;

public class User {
	//public final static ArrayList<User> UserList = new ArrayList<>();
	private String username;
	private ArrayList<Album> albums;
	
	public User (String name) {
		this.username = name;
		this.albums = new ArrayList<Album>();
	}
	
	public String getUsername() {
		return username;
	}
	
	public boolean createAlbum(String albumName) {
		for (int i = 0; i < albums.size(); i++) {
			if (albumName == albums.get(i).getAlbumName()) return false;
		}
		
		albums.add(new Album(albumName));
		return true;
	}
	
	
	public boolean deleteAlbum(String albumName) {
		for (int i = 0; i < albums.size(); i++) {
			if (albumName == albums.get(i).getAlbumName()) {
				albums.remove(i);
				return true;
			}
		}
		return false;
	}
	
	
	public boolean renameAlbum(String oldName, String newName) {
		if (oldName == newName) return true;
		for (int i = 0; i < albums.size(); i++) {
			if (oldName == albums.get(i).getAlbumName()) {
				albums.get(i).setName(newName);
				return true;
			}
		}
		return false;
	}
	
	public void addPhoto(String albumName, Photo photo) {
		for (int i = 0; i < albums.size(); i++) {
			if (albumName == albums.get(i).getAlbumName()) {
				albums.get(i).addPhoto(photo);
				break;
			}
		}
	}
	
	public boolean deletePhoto(String albumName, Photo photo) {
		for (int i = 0; i < albums.size(); i++) {
			if (albumName == albums.get(i).getAlbumName()) {
				if (!(albums.get(i).deletePhoto(photo))) {
					return false;
				} else {
					return true;
				}
			}
		}
		return false;
	}
	
	
}
