package ftn.xws.contentservice.dto;

public class CommentDTO {
	private long id;
	private String content;
	
	public CommentDTO() {
		super();
	}
	public CommentDTO(long id, String content) {
		super();
		this.id = id;
		this.content = content;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
