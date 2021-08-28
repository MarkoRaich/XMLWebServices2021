package ftn.xws.contentservice.dto;

import java.util.List;

public class AlbumDTO {
	private boolean postSelected;
	private boolean closeFriends;
	private List<String> tags;
	private String location;
	private String description;
	
	public AlbumDTO() {
		super();
	}

	public AlbumDTO(boolean postSelected, boolean closeFriends, List<String> tags, String location,
			String description) {
		super();
		this.postSelected = postSelected;
		this.closeFriends = closeFriends;
		this.tags = tags;
		this.location = location;
		this.description = description;
	}

	public boolean isPostSelected() {
		return postSelected;
	}

	public void setPostSelected(boolean postSelected) {
		this.postSelected = postSelected;
	}

	public boolean isCloseFriends() {
		return closeFriends;
	}

	public void setCloseFriends(boolean closeFriends) {
		this.closeFriends = closeFriends;
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
