package ftn.xws.contentservice.dto;

import java.util.Set;

public class AllReactionsDTO {
	private Set<String> likes;
	private Set<String> dislikes;
	
	public AllReactionsDTO() {
		super();
	}
	
	public AllReactionsDTO(Set<String> likes, Set<String> dislikes) {
		super();
		this.likes = likes;
		this.dislikes = dislikes;
	}
	public Set<String> getLikes() {
		return likes;
	}
	public void setLikes(Set<String> likes) {
		this.likes = likes;
	}
	public Set<String> getDislikes() {
		return dislikes;
	}
	public void setDislikes(Set<String> dislikes) {
		this.dislikes = dislikes;
	}
}
