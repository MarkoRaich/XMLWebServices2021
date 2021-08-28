package ftn.xws.contentservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import ftn.xws.contentservice.enumeration.RatingType;

@Entity
public class Rating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "username", nullable = false)
	private String username;
	
	@Column(name = "ratingType", nullable = false)
	private RatingType ratingType;

	
	public Rating() {
		super();
	}

	public Rating(long id, String username, RatingType ratingType) {
		super();
		this.id = id;
		this.username = username;
		this.ratingType = ratingType;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public RatingType getRatingType() {
		return ratingType;
	}

	public void setRatingType(RatingType ratingType) {
		this.ratingType = ratingType;
	}
}
