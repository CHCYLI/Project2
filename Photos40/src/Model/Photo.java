package Model;
import java.util.Calendar;
import java.util.HashMap;
import java.io.Serializable;
import java.util.ArrayList;

public class Photo implements Serializable{
	/**
	 * 
	 */
	//public static final long serialVersionUID = 1L;
	private String namePhoto;
	private Calendar cal;
	private String caption;
	private HashMap<String,ArrayList<String>> tags;
	
	/*
	 * @param namePhoto image name in system
	 * @param cal date of the image
	 * @param caption caption of the image
	 * @tags tags of the image
	 */
	public Photo(String namephoto, Calendar cal, String caption, HashMap<String,ArrayList<String>> tags) {
        this.namePhoto = namephoto;
        this.cal = cal;
        this.caption = caption;
        this.tags = tags;
    }
	
	/*
	 * @newPath set namePhoto for image
	 */
	public void setNamePhoto(String newnamePhoto) {
		this.namePhoto = newnamePhoto;
	}
	
	/*
	 * @return return namePhoto of the image
	 */
	public String getNamePhoto() {
		return namePhoto;
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