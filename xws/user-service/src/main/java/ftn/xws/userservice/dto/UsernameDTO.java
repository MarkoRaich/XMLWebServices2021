package ftn.xws.userservice.dto;

public class UsernameDTO {

    private String username;

    public UsernameDTO() {}

    public UsernameDTO(String username) {
        super();
        this.username = username;
    }

    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}