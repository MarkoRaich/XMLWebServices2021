package ftn.xws.contentservice.dto;

public class ReactionsNumberDTO {
	private int likes;
	private int dislikes;
	
	public ReactionsNumberDTO() {
		super();
	}
	
	public ReactionsNumberDTO(int likes, int dislikes) {
		super();
		this.likes = likes;
		this.dislikes = dislikes;
	}
	
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public int getDislikes() {
		return dislikes;
	}
	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}
	
	
}
