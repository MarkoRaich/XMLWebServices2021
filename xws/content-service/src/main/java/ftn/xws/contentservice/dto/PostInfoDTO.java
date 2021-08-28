package ftn.xws.contentservice.dto;

import java.time.LocalDateTime;
import java.util.Set;

public class PostInfoDTO {
   
	private long id;
    private String username;
    private Set<String> urls;
    private String location;
    private String description;
    private Set<String> hashtags;
    private LocalDateTime created;

    public PostInfoDTO() {

    }

    public PostInfoDTO(long id, String username, Set<String> urls, String location, String description, Set<String> hashtags, LocalDateTime created) {
        this.id = id;
        this.username = username;
        this.urls = urls;
        this.location = location;
        this.description = description;
        this.hashtags = hashtags;
        this.created = created;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getUrls() {
        return urls;
    }

    public void setUrls(Set<String> urls) {
        this.urls = urls;
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

    public Set<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(Set<String> hashtags) {
        this.hashtags = hashtags;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
