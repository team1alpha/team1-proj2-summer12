package mobile.group1.DB;

import android.graphics.Bitmap;

public class Item 
{
	private Bitmap    image;
	private String    description;
	private String    found_by;
	private String    placeholder1;
	private String    placeholder2;
	
	public Bitmap getImage() {
		return image;
	}
	public void setImage(Bitmap image) {
		this.image = image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFound_by() {
		return found_by;
	}
	public void setFound_by(String found_by) {
		this.found_by = found_by;
	}
	public String getPlaceholder1() {
		return placeholder1;
	}
	public void setPlaceholder1(String placeholder1) {
		this.placeholder1 = placeholder1;
	}
	public String getPlaceholder2() {
		return placeholder2;
	}
	public void setPlaceholder2(String placeholder2) {
		this.placeholder2 = placeholder2;
	}
}
