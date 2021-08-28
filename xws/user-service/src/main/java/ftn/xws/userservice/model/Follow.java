package ftn.xws.userservice.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Follow {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

	//korisnik koji je vlasnik veza
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private User user;

    //korisnik koji ga prati
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private User follower;
    
    @Column(name = "closeFriend", nullable = false)
    private boolean closeFriend;

    @Column(name = "muted", nullable = false)
    private boolean muted;
    
    @Column(name = "blocked", nullable = false)
    private boolean blocked;
    
    
    public Follow() {}

    
    public Follow(User user, User follower, boolean closeFriend, boolean muted, boolean blocked) {
        this.user = user;
        this.follower = follower;
        this.closeFriend = closeFriend;
        this.muted = muted;
        this.blocked = blocked;
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


	public User getFollower() {
		return follower;
	}


	public void setFollower(User follower) {
		this.follower = follower;
	}


	public boolean isCloseFriend() {
		return closeFriend;
	}


	public void setCloseFriend(boolean closeFriend) {
		this.closeFriend = closeFriend;
	}


	public boolean isMuted() {
		return muted;
	}


	public void setMuted(boolean muted) {
		this.muted = muted;
	}


	public boolean isBlocked() {
		return blocked;
	}


	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
    
    
    
    
	
}
