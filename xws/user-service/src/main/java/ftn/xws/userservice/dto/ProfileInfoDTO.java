package ftn.xws.userservice.dto;

import java.time.LocalDate;

public class ProfileInfoDTO {

    
    private long id;
	private String username;
	private String fullName;
	private String bio;
	private LocalDate birthDate;
	private String email;
	private String gender;
	private String website;
	private int followerCount;
	private int followingCount;
	private boolean owned;
	private boolean privateProfile;
	private boolean following;
	private boolean verified;

	public ProfileInfoDTO() {

	}

	public ProfileInfoDTO(long id, String username, String fullName, String bio, LocalDate birthDate, String email,
			String gender, String website, int followerCount, int followingCount, boolean owned, boolean privateProfile,
			boolean following, boolean verified) {
		this.id = id;
		this.username = username;
		this.fullName = fullName;
		this.bio = bio;
		this.birthDate = birthDate;
		this.email = email;
		this.gender = gender;
		this.website = website;
		this.followerCount = followerCount;
		this.followingCount = followingCount;
		this.owned = owned;
		this.privateProfile = privateProfile;
		this.following = following;
		this.verified = verified;
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public int getFollowerCount() {
		return followerCount;
	}

	public void setFollowerCount(int followerCount) {
		this.followerCount = followerCount;
	}

	public int getFollowingCount() {
		return followingCount;
	}

	public void setFollowingCount(int followingCount) {
		this.followingCount = followingCount;
	}

	public boolean isOwned() {
		return owned;
	}

	public void setOwned(boolean owned) {
		this.owned = owned;
	}

	public boolean isPrivateProfile() {
		return privateProfile;
	}

	public void setPrivateProfile(boolean privateProfile) {
		this.privateProfile = privateProfile;
	}

	public boolean isFollowing() {
		return following;
	}

	public void setFollowing(boolean following) {
		this.following = following;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	
}

