package ftn.xws.userservice.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class FollowRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	//korisnik kome je zahtev poslat
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private User user;
	
	//posiljalac zahteva
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private User requester;
	
	public FollowRequest() {
		super();
	}

	public FollowRequest(long id, User user, User requester) {
		super();
		this.id = id;
		this.user = user;
		this.requester = requester;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getRequester() {
		return requester;
	}

	public void setRequester(User requester) {
		this.requester = requester;
	}

	
	
}
