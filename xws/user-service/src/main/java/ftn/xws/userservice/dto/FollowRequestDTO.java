package ftn.xws.userservice.dto;

public class FollowRequestDTO {
	private String username1; //username od profila na koji se salju requestovi
	private String username2;
	private long id;
	
	public FollowRequestDTO() {
		super();
	}
	
	public FollowRequestDTO(String username1, String username2, long id) {
		super();
		this.username1 = username1;
		this.username2 = username2;
		this.id = id;
	}
	
	public String getUsername1() {
		return username1;
	}
	public void setUsername1(String username1) {
		this.username1 = username1;
	}
	public String getUsername2() {
		return username2;
	}
	public void setUsername2(String username2) {
		this.username2 = username2;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

}
