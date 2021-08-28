package ftn.xws.contentservice.dto;

import java.util.List;
import java.util.Set;

public class CollectionInfoDTO {
	private long id;
	private String name;
	private Set<String> urls;
	
	public CollectionInfoDTO() {
		super();
	}
	
	public CollectionInfoDTO(long id, String name, Set<String> urls) {
		super();
		this.id = id;
		this.name = name;
		this.urls = urls;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getUrls() {
		return urls;
	}

	public void setUrls(Set<String> urls) {
		this.urls = urls;
	}

}
