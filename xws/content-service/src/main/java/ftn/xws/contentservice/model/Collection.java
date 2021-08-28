package ftn.xws.contentservice.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Collection {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "username", nullable = false)
	private String username;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Favourites favourite;

	public Collection() {}
	
	public Collection(long id, String name, String username, Favourites favourite) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.favourite = favourite;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Favourites getFavourite() {
		return favourite;
	}

	public void setFavourite(Favourites favourite) {
		this.favourite = favourite;
	}
}
