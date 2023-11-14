package Entity;
import java.util.Calendar;
import java.util.HashMap;
import java.util.ArrayList;

public class Photo {
	private String path;
	private Calendar cal;
	private String caption;
	private HashMap<String,ArrayList<String>> tags;
	
	
	
	/*
	 * @param path image path in system
	 * @param cal date of the image
	 * @param caption caption of the image
	 * @tags tags of the image
	 */
	public Photo(String path, Calendar cal, String caption, HashMap<String,ArrayList<String>> tags) {
        this.path = path;
        this.cal = cal;
        this.caption = caption;
        this.tags = tags;
    }
	
	/*
	 * @newPath set path for image
	 */
	public void setPath(String newPath) {
		this.path = newPath;
	}
	
	/*
	 * @return return path of the image
	 */
	public String getPath() {
		return path;
	}
	
	/*
	 * @return return date and time of the image
	 */
	public Calendar getCalendar() {
		return cal;
	}
	
	/*
	 * @param newCaption set up caption
	 */
	public void setCaption(String newCaption) {
		this.caption = newCaption;
	}
	
	/*
	 * @return return caption of the image
	 */
	public String getCaption() {
		return caption;
	}
	
	/*
	 * @param newTag set up tag for the image
	 */
	public void setTags (HashMap<String,ArrayList<String>> newTag) {
		this.tags = newTag;
	}
	
	/*
	 * @return return tags of the image
	 */
	public HashMap<String,ArrayList<String>> getTags() {
		return tags;
	}
	
	/*
	 * @param name name of the tag
	 * @param value value of the tag
	 */
	public boolean addTag (String name, String value) {
		if (tags == null) tags = new HashMap<String,ArrayList<String>>();
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
	
	/*
	 * @param name name of the tag
	 * @param value value of the tag
	 */
	public void deleteTag (String name, String value) {
		if(tags.containsKey(name) && tags.get(name).contains(value))
		tags.get(name).remove(value);
		//tags.remove(name);
	}
	
	/*
	 * @param tagName
	 * @return if photo has this tag name
	 */
	public boolean hasTagName(String tagName) {
		return tags.containsKey(tagName);
	}
	
	/*
	 * @param tagName
	 * @param tagValue
	 * @return if photo has this tag value
	 */
	public boolean hasTagValue(String tagName, String tagValue) {
		if (hasTagName(tagName)) {
			return tags.get(tagName).contains(tagValue);
		}
		return false;
	}
}
