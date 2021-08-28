package ftn.xws.userservice.model;

import java.util.Collection;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ftn.xws.userservice.dto.UserDTO;
import ftn.xws.userservice.enumeration.Gender;
import ftn.xws.userservice.enumeration.UserType;

@Entity(name="users")
public class User implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Email cannot be null.") //email
    @Column(nullable = false, unique = true)
	private String username;

    @JsonIgnore
    @NotNull(message = "Password cannot be null.")
    @Column(nullable = false)
    private String password;
    
    @NotNull(message = "Account name cannot be null.")
    @Column(nullable = false, unique = true)
	private String accountName;

    @Column(columnDefinition = "VARCHAR(30)", nullable = true)
    private String firstName;

    @Column(columnDefinition = "VARCHAR(30)", nullable = true)
    private String lastName;
    
    @Column(columnDefinition = "VARCHAR(250)")
    private String biography;
    
    @Column(columnDefinition = "VARCHAR(30)", nullable = false)
    private String phoneNumber;
    
    @Column(columnDefinition = "VARCHAR(10)", nullable = false)
    private String dateOfBirth;
    
    @Enumerated(EnumType.STRING)
    private Gender gender;
    
    @Enumerated(EnumType.STRING)
    private UserType type;
    
    @Column(columnDefinition = "VARCHAR(30)")
    private String websiteLink;
    
    @Column(nullable=false)
    private boolean isPublic;
    
    @Column(nullable=false)
    private boolean isVerified;
    
    @Column(nullable=false)
    private boolean isRemoved;

    @Column(nullable=false)
    private boolean canBeTagged;
    
    @Column(nullable=false)
    private boolean canReceiveMessages;
    
    @Column(nullable=false)
    private boolean canReceiveNotifications;
    
    @Column(name = "active" , nullable = false)
	private boolean active;
    
    //vezano za prava pristupa spring security
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private Set<Authority> authorities;
    
    
    
    public User() {}



	public User(@Valid UserDTO userDTO) {
		
		this.username = userDTO.getEmail();
		this.accountName = userDTO.getAccountName();
		this.firstName = userDTO.getFirstName();
		this.lastName = userDTO.getLastName();
		this.biography = userDTO.getBiography();
		this.phoneNumber = userDTO.getPhoneNumber();
		this.dateOfBirth = userDTO.getDateOfBirth();
		this.gender = Gender.valueOf(userDTO.getGender());
		this.websiteLink = userDTO.getWebsiteLink();
		this.isPublic = true;
		this.isVerified = false;
		this.isRemoved = false;
		this.canBeTagged = true;
		this.canReceiveMessages = true;
		this.canReceiveNotifications = true;
		
	}


	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}







	public Gender getGender() {
		return gender;
	}



	public void setGender(Gender gender) {
		this.gender = gender;
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



	public void setPassword(String password) {
		this.password = password;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return this.authorities;
	}



	@Override
	public String getPassword() {

		return this.password;
	}



	@Override
	public String getUsername() {

		return this.username;
	}



	@Override
	public boolean isAccountNonExpired() {

		return true;
	}



	@Override
	public boolean isAccountNonLocked() {

		return true;
	}



	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}



	@Override
	public boolean isEnabled() {
		
		return true;
	}



	public String getAccountName() {
		return accountName;
	}



	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}



	public UserType getType() {
		return type;
	}



	public void setType(UserType type) {
		this.type = type;
	}



	public boolean isActive() {
		return active;
	}



	public void setActive(boolean active) {
		this.active = active;
	}



	public User(@NotNull(message = "Email cannot be null.") String username) {
		super();
		this.username = username;
	}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
