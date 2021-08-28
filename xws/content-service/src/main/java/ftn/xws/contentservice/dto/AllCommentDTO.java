package ftn.xws.contentservice.dto;

public class AllCommentDTO {
	public String username;
	public String content;
	
	
	public AllCommentDTO() {
		super();
	}
	public AllCommentDTO(String username, String content) {
		super();
		this.username = username;
		this.content = content;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
