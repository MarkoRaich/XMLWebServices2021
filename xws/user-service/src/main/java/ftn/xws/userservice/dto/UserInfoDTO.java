package ftn.xws.userservice.dto;


import javax.validation.constraints.NotEmpty;

import ftn.xws.userservice.model.User;

public class UserInfoDTO {

	@NotEmpty(message = "Username name is empty.")
    private String username;
	
	@NotEmpty(message = "Account name is empty.")
    private String accountName;
	
	@NotEmpty(message = "First Name is empty.")
    private String firstName;
    
	@NotEmpty(message = "Last Name is empty.")
    private String lastName;
	
    private String biography;
	
	@NotEmpty(message = "Phone number is empty.")
    private String phoneNumber;
    
	@NotEmpty(message = "Date of Birth Name is empty.")
    private String dateOfBirth;

	@NotEmpty(message = "Gender is empty.")
	private String gender;
	
	
	private String website;
	
	private boolean userIsPublic;
	
	private boolean canReceiveMessages;

	private boolean canBeTagged;
	
	private boolean canReceiveNotifications;
	
	
	public UserInfoDTO() {}
	
	public UserInfoDTO(User user) {
		
		this.username = user.getUsername();
		this.accountName =user.getAccountName();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.biography = user.getBiography();
		this.phoneNumber = user.getPhoneNumber();
		this.dateOfBirth = user.getDateOfBirth();
		this.gender = user.getGender().toString();
		this.website = user.getWebsiteLink();
		this.userIsPublic = user.isPublic();
		this.canReceiveMessages = user.isCanReceiveMessages();
		this.canBeTagged = user.isCanBeTagged();
		this.canReceiveNotifications = user.isCanReceiveNotifications();
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isUserIsPublic() {
		return userIsPublic;
	}

	public void setUserIsPublic(boolean userIsPublic) {
		this.userIsPublic = userIsPublic;
	}

	public boolean isCanReceiveMessages() {
		return canReceiveMessages;
	}

	public void setCanReceiveMessages(boolean canReceiveMessages) {
		this.canReceiveMessages = canReceiveMessages;
	}

	public boolean isCanBeTagged() {
		return canBeTagged;
	}

	public void setCanBeTagged(boolean canBeTagged) {
		this.canBeTagged = canBeTagged;
	}

	public boolean isCanReceiveNotifications() {
		return canReceiveNotifications;
	}

	public void setCanReceiveNotifications(boolean canReceiveNotifications) {
		this.canReceiveNotifications = canReceiveNotifications;
	}

	@Override
	public String toString() {
		return "UserInfoDTO [username=" + username + ", accountName=" + accountName + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", biography=" + biography + ", phoneNumber=" + phoneNumber
				+ ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", website=" + website + ", userIsPublic="
				+ userIsPublic + ", canReceiveMessages=" + canReceiveMessages + ", canBeTagged=" + canBeTagged
				+ ", canReceiveNotifications=" + canReceiveNotifications + "]";
	}

	
	
	
}
