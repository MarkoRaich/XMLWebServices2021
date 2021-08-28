package ftn.xws.contentservice.dto;

import java.util.List;

public class PostDTO {
	private List<String> tags;
	private String location;
	private String description;
	
	public PostDTO() {
		super();
	}
	public PostDTO(List<String> tags, String location, String description) {
		super();
		this.tags = tags;
		this.location = location;
		this.description = description;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
