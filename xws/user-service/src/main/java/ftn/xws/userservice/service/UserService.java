package ftn.xws.userservice.service;


import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.core.io.UrlResource;

import ftn.xws.userservice.dto.FollowRequestDTO;

import ftn.xws.userservice.dto.FollowerDTO;
import ftn.xws.userservice.dto.ProfileInfoDTO;

import ftn.xws.userservice.dto.UserInfoDTO;
import ftn.xws.userservice.exception.ProfileNotFoundException;
import ftn.xws.userservice.model.User;

public interface UserService {

	boolean emailIsUsed(String email);

	boolean accountNameIsUsed(String accountName);

	User getLoginUser();

	User changeUsersInfo(@Valid UserInfoDTO userDTO);

	String getAccountName(String username);
	
	int followProfile(String username, String loggedInUsername);

	int unfollowProfile(String username, String loggedInUsername);
	
	List<FollowerDTO> getFollowers(String username);
	
	Set<FollowRequestDTO> getFollowRequests(String username);
	
	Set<FollowRequestDTO> acceptRequest(String username,String loggedInUsername);

	Set<FollowRequestDTO> deleteRequest(String username,String loggedInUsername);
	
	UrlResource getContent(String fileName) throws MalformedURLException;
	
	List<FollowerDTO> getFollowing(String username);
	
	User get(String username);
	
	String addCloseFriend(String myUsername, String usernameOfFriend);

	String removeCloseFriend(String myUsername, String usernameOfFriend);
	
	//METODE ZA KOMUNIKACIJU SA CONTENT SERVICE-OM
	List<String> getAll();

	List<String> getFollowerss(String username);
	
	List<String> getFollowingg(String username);
	
	List<String> getBlocked(String profile) throws UnsupportedEncodingException;

	List<String> getMuted(String profile);

	List<String> getCloseFriends(String profile);

	boolean isPublic(String profile) throws Exception;
	
	boolean isPublicForUsername(String username) throws Exception;
	
	void deactivateProfile(String username);
	
	List<String> getAllInactiveProfiles();


	ProfileInfoDTO getProfile(String requestedBy, String username) throws ProfileNotFoundException ;

	User getUserFromUsername(String username);

	String getUsernameFromAccountName(String accountName);



}
