package ftn.xws.userservice.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import ftn.xws.userservice.model.User;

public class UserDTO {
	
	
	@NotEmpty(message = "Email is empty.")
	@Email(message = "Email is invalid.")
	private String email;
	
	@NotEmpty(message = "Password is empty.")
	private String password;
	
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
	


	private String type;
	
	private String websiteLink;
	
	private boolean isPublic;
	
	private boolean isVerified;
	
	private boolean isRemoved;
	
	private boolean canBeTagged;
	
	private boolean canReceiveMessages;
	
	private boolean canReceiveNotifications;
	
	
	public UserDTO() {}


	public UserDTO(User user) {
		
		this.email = user.getUsername();
		this.accountName = user.getAccountName();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.biography = user.getBiography();
		this.phoneNumber = user.getPhoneNumber();
		this.dateOfBirth = user.getDateOfBirth();
		this.gender = user.getGender().toString();
		
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
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


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getWebsiteLink() {
		return websiteLink;
	}


	public void setWebsiteLink(String websiteLink) {
		this.websiteLink = websiteLink;
	}


	public boolean isPublic() {
		return isPublic;
	}


	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}


	public boolean isVerified() {
		return isVerified;
	}


	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}


	public boolean isRemoved() {
		return isRemoved;
	}


	public void setRemoved(boolean isRemoved) {
		this.isRemoved = isRemoved;
	}


	public boolean isCanBeTagged() {
		return canBeTagged;
	}


	public void setCanBeTagged(boolean canBeTagged) {
		this.canBeTagged = canBeTagged;
	}


	public boolean isCanReceiveMessages() {
		return canReceiveMessages;
	}


	public void setCanReceiveMessages(boolean canReceiveMessages) {
		this.canReceiveMessages = canReceiveMessages;
	}


	public boolean isCanReceiveNotifications() {
		return canReceiveNotifications;
	}


	public void setCanReceiveNotifications(boolean canReceiveNotifications) {
		this.canReceiveNotifications = canReceiveNotifications;
	}
	
	
	
}
