package ftn.xws.contentservice.dto;

import java.time.LocalDateTime;

public class StoryInfoDTO {

	private long id;
    private String username;
    private String url;
    private LocalDateTime created;

    public StoryInfoDTO() {

    }

    public StoryInfoDTO(long id,String username, String url, LocalDateTime created) {
        this.id = id;
    	this.username = username;
        this.url = url;
        this.created = created;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
    
    
}
