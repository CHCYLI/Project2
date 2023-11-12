package Entity;
import java.util.Calendar;
import java.util.HashMap;
import java.util.ArrayList;

public class Photo {
	private String path;
	private Calendar cal;
	private String caption;
	private HashMap<String,ArrayList<String>> tags;
	
	
	private Photo(String path, Calendar cal, String caption, HashMap<String,ArrayList<String>> tags) {
        this.path = path;
        this.cal = cal;
        this.caption = caption;
        this.tags = tags;
    }
	
	public String getPath() {
		return path;
	}
	
	public Calendar getCalendar() {
		return cal;
	}
	
	public String getCaption() {
		return caption;
	}
	
	public HashMap<String,ArrayList<String>> getTags() {
		return tags;
	}
	
	public boolean addTag (String name, String value) {
		if (!tags.containsKey(name)) { //if not found same name
			tags.put(name, new ArrayList<String>());
			if(!tags.get(name).contains(value)) { //if not found same value, a tag is created
				tags.get(name).add(value);
			} else {				// otherwise, return false
				return false;
			}
		}
		return true;
	}
	
	public void deleteTag (String name, String value) {
		if(tags.containsKey(name) && tags.get(name).contains(value))
		tags.get(name).remove(value);
		//tags.remove(name);
	}
}
