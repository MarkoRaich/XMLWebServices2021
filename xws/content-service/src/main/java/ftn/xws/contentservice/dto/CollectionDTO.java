package ftn.xws.contentservice.dto;

public class CollectionDTO {

	private String name;
	private long id;
	
	public CollectionDTO() {}
	
	public CollectionDTO(String name, long id) {
		super();
		this.name = name;
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
}
