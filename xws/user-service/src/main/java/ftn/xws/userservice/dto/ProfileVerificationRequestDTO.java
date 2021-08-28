package ftn.xws.userservice.dto;

public class ProfileVerificationRequestDTO {
	private long id;
	private String name;
	private String surname;
	private String category;
	private String url;
	
	public ProfileVerificationRequestDTO() {}
	
	public ProfileVerificationRequestDTO(long id,String name, String surname, String category, String url) {
		super();
		this.id =id;
		this.name = name;
		this.surname = surname;
		this.category = category;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
		
}
