package ftn.xws.contentservice.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.CascadeType;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Story {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "dateCreated", nullable = false)
	private LocalDateTime dateCreated;

	@Column(name = "closeFriends", nullable = false)
	private boolean closeFriends;

	@Column(name = "expiresInHours", nullable = false)
	private final int expiresInHours = 24;

	@Column(name = "isHighlighted", nullable = false)
	private boolean isHighlighted;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Media media;

	public Story() {
		super();
	}

	public Story(long id, LocalDateTime dateCreated, boolean closeFriends, boolean isHighlighted,
			Media media) {
		super();
		this.id = id;
		this.dateCreated = dateCreated;
		this.closeFriends = closeFriends;
		this.isHighlighted = isHighlighted;
		this.media = media;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public boolean isCloseFriends() {
		return closeFriends;
	}

	public void setCloseFriends(boolean closeFriends) {
		this.closeFriends = closeFriends;
	}

	public int getExpiresInHours() {
		return expiresInHours;
	}

	public boolean isHighlighted() {
		return isHighlighted;
	}

	public void setHighlighted(boolean isHighlighted) {
		this.isHighlighted = isHighlighted;
	}

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}
	
	public boolean isActive() {
		LocalDateTime date = LocalDateTime.now();
		long hours = ChronoUnit.HOURS.between(this.dateCreated, date);
		if(hours < this.expiresInHours) {
			return true;
		}else {
			return false;
		}
		
	}
}
