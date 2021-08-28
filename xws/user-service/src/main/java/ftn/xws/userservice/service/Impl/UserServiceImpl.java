package ftn.xws.userservice.service.Impl;

import java.io.File;

import java.io.UnsupportedEncodingException;

import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ftn.xws.userservice.dto.FollowRequestDTO;

import ftn.xws.userservice.dto.FollowerDTO;
import ftn.xws.userservice.dto.ProfileInfoDTO;
import ftn.xws.userservice.dto.UserInfoDTO;
import ftn.xws.userservice.enumeration.Gender;
import ftn.xws.userservice.exception.ProfileNotFoundException;
import ftn.xws.userservice.model.Admin;
import ftn.xws.userservice.model.Follow;
import ftn.xws.userservice.model.FollowRequest;
import ftn.xws.userservice.model.User;
import ftn.xws.userservice.repository.AdminRepository;
import ftn.xws.userservice.repository.FollowRepository;
import ftn.xws.userservice.repository.FollowRequestRepository;
import ftn.xws.userservice.repository.UserRepository;
import ftn.xws.userservice.service.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private FollowRepository followRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FollowRequestRepository followRequestRepo; 
	

	@Value("${user.storage}")
	private String storageDirectoryPath;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDetails userDetails = searchUserInAllRepositories(username);
		if (userDetails != null) {
            return userDetails;
        }
        throw new UsernameNotFoundException(String.format("No user found with email '%s'.", username));
		
	}
	
	

	//trazi Usera u svim repozitorijumima
	public UserDetails searchUserInAllRepositories(String username) {
			
			 try {
		            Admin admin = adminRepository.findOneByUsername(username);
		            if (admin != null) {
		                return admin;
		            }
		        } catch (UsernameNotFoundException ex) {

		        }
			 try {
		            User user = userRepository.findOneByUsername(username);
		            if (user != null) {
		                return user;
		            }
		        } catch (UsernameNotFoundException ex) {

		        }
			return null;
	}


		
	
	public boolean emailIsUsed(String email) {
		 
		try {
	            Admin admin = adminRepository.findByUsername(email);
	            if (admin != null) {
	                return true;
	            }
	        } catch (UsernameNotFoundException ex) {

	        }

	        try {
	            User user = userRepository.findByUsername(email);
	            if (user != null) {
	                return true;
	            }
	        } catch (UsernameNotFoundException ex) {

	        }

	   return false;

	}



	@Override
	public boolean accountNameIsUsed(String accountName) {
		
        try {
            User user = userRepository.findByAccountName(accountName);
            if (user != null) {
                return true;
            }
        } catch (UsernameNotFoundException ex) {

        }

        return false;
	}



	@Override
	public User getLoginUser() {

	Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
    try {
    	//System.out.println(currentUser.getName());
    	User user = userRepository.findOneByUsername(currentUser.getName());
        if (user != null) {
            return user;
        }
    } catch (UsernameNotFoundException ex) {

    }
    return null;
}



	@Override
	public User changeUsersInfo(@Valid UserInfoDTO userDTO) {
		
		System.out.println(userDTO);
		
		User user = userRepository.findOneByUsername(userDTO.getUsername());
		
		user.setAccountName(userDTO.getAccountName());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setBiography(userDTO.getBiography());
		user.setPhoneNumber(userDTO.getPhoneNumber());
		user.setGender(Gender.valueOf(userDTO.getGender()));
		user.setDateOfBirth(userDTO.getDateOfBirth());
		user.setWebsiteLink(userDTO.getWebsite());
		user.setPublic(userDTO.isUserIsPublic());
		user.setCanBeTagged(userDTO.isCanBeTagged());
		user.setCanReceiveMessages(userDTO.isCanReceiveMessages());
		user.setCanReceiveNotifications(userDTO.isCanReceiveNotifications());
		
	    userRepository.save(user);
	    
	    return user;

	}


	@Override
	public String getAccountName(String username) {
		return userRepository.findByUsername(username).getAccountName();
	}

	//METODE ZA KOMUNIKACIJU SA CONTENT SERVICE-OM

	@Override
	public List<String> getAll() {
		
		return userRepository.findAll().stream().map(p -> p.getAccountName()).collect(Collectors.toList());

	}

	
	@Override
	public List<String> getFollowerss(String accountName) {
		
		List<String> following = new ArrayList<String>();
		
		for (Follow f : followRepository.findAll().stream().filter(f-> !f.isBlocked()).collect(Collectors.toList())) {
			if (f.getUser().getAccountName().equals(accountName)) {
				following.add(f.getFollower().getAccountName());
			}
		}
		
		return following;
	}

	@Override
	public List<String> getFollowingg(String accountName) {
		
		List<String> following = new ArrayList<String>();
		
		for (Follow f : followRepository.findAll().stream().filter(f-> !f.isBlocked()).collect(Collectors.toList())) {
			if (f.getFollower().getAccountName().equals(accountName)) {
				following.add(f.getUser().getAccountName());
			}
		
		}
		return following;
	}

	@Override
	public List<String> getBlocked(String username) throws UnsupportedEncodingException {
		
		String email = URLDecoder.decode(username, "UTF-8" );
		
		return followRepository.findAll().stream()
				.filter(f -> f.getFollower().getUsername().equals(email) && f.isBlocked())
				.map(f -> f.getUser().getUsername()).collect(Collectors.toList());
	}

	@Override
	public List<String> getMuted(String username) {
		return followRepository.findAll().stream()
				.filter(f -> f.getFollower().getUsername().equals(username) && f.isMuted())
				.map(f -> f.getUser().getUsername()).collect(Collectors.toList());
	}

	@Override
	public List<String> getCloseFriends(String profile) {
		return followRepository.findAll().stream()
				.filter(f -> f.getUser().getUsername().equals(profile) && f.isCloseFriend())
				.map(f -> f.getFollower().getUsername()).collect(Collectors.toList());
	}

	@Override
	public boolean isPublic(String profile) throws Exception {
		
		Optional<User> p = userRepository.findAll().stream()
				.filter(prof -> prof.getAccountName().equals(profile)).findFirst();
		if (!p.isPresent())
			throw new Exception();
		
		return p.get().isPublic();
	}
	
	

	@Override
	public boolean isPublicForUsername(String username) throws Exception {

		Optional<User> p = userRepository.findAll().stream()
				.filter(prof -> prof.getUsername().equals(username)).findFirst();
		if (!p.isPresent())
			throw new Exception();
		
		return p.get().isPublic();
		
	}


	@Override
	public void deactivateProfile(String username) {
		User user = userRepository.findByUsername(username);
		user.setRemoved(true);
		userRepository.save(user);
	}

	@Override
	public List<String> getAllInactiveProfiles() {
		List<String> results = new ArrayList<String>();
		for(User profile : userRepository.findAll()) {
			if(profile.isRemoved() == true) {
				results.add(profile.getUsername());
			}
		}
		return results;
	}



	@Override
	public int followProfile(String username, String loggedInUsername) {
		
		User profile = userRepository.findOneByAccountName(username);
		User followedBy = userRepository.findOneByUsername(loggedInUsername);

		if (profile.isPublic()) {

			Follow follow = new Follow();
			follow.setFollower(followedBy);
			follow.setUser(profile);
			follow.setBlocked(false);
			follow.setCloseFriend(false);
			follow.setMuted(false);

			follow = followRepository.save(follow);
			/*sendNotification(NotificationType.NEW_FOLLOW, follow.getProfile().getRegularUserUsername(),
					follow.getFollowedBy().getRegularUserUsername(),
					follow.getFollowedBy().getRegularUserUsername());
			notificationService.createSettings(follow.getId(), follow.getProfile().getRegularUserUsername());*/
		} else {
			FollowRequest request = new FollowRequest();
			request.setUser(profile);
			request.setRequester(followedBy);
			boolean exists = false;

			for (FollowRequest f : followRequestRepo.findAll()) {
				if (f.getRequester().equals(followedBy) && f.getUser().equals(profile)) {
					exists = true;
				}
			}
			if (!exists) {
				request = followRequestRepo.save(request);
				/*sendNotification(NotificationType.NEW_FOLLOW_REQUEST, request.getProfile().getRegularUserUsername(),
						request.getFollowedBy().getRegularUserUsername(),
						request.getFollowedBy().getRegularUserUsername());*/
			}
		}
		return getFollowers(username).size();
	}



	@Override
	public int unfollowProfile(String username, String loggedInUsername) {
		
		Follow delete = new Follow();
		
		for (Follow f : followRepository.findAll()) {
			if (f.getUser().getAccountName().equals(username)
					&& f.getFollower().getUsername().equals(loggedInUsername)) {
				delete = f;
				break;
			}
		}
		followRepository.delete(delete);
		//notificationService.deleteSettings(delete.getId());

		return getFollowers(username).size();
	}



	@Override
	public List<FollowerDTO> getFollowers(String username) {
		List<FollowerDTO> followers = new ArrayList<FollowerDTO>();
		for (Follow f : followRepository.findAll().stream().filter(f -> !f.isBlocked() && f.getFollower().isActive()).collect(Collectors.toList())) {
			if (f.getUser().getAccountName().equals(username)) {
				followers.add(new FollowerDTO(f.getFollower().getAccountName()));
			}
		}
		return followers;
	}



	@Override
	public Set<FollowRequestDTO> getFollowRequests(String username) {
		
		Set<FollowRequest> requests = followRequestRepo.findByUser(userRepository.findOneByAccountName(username));
		Set<FollowRequestDTO> followRequests = new HashSet<FollowRequestDTO>();

		for (FollowRequest f : requests) {
			FollowRequestDTO dto = new FollowRequestDTO();
			dto.setUsername1(f.getUser().getAccountName());
			dto.setUsername2(f.getRequester().getAccountName());
			dto.setId(f.getId());
			followRequests.add(dto);
		}
		return followRequests;
	}



	@Override
	public Set<FollowRequestDTO> acceptRequest(String username, String loggedInUsername) {
		
		User profile = userRepository.findOneByUsername(loggedInUsername);
		User followedBy = userRepository.findOneByAccountName(username);

		Follow follow = new Follow();
		follow.setFollower(followedBy);
		follow.setUser(profile);
		follow.setBlocked(false);
		follow.setCloseFriend(false);
		follow.setMuted(false);

		follow = followRepository.save(follow);
		/*sendNotification(NotificationType.FOLLOW_REQUEST_ACCEPTED, follow.getFollowedBy().getRegularUserUsername(),
				follow.getProfile().getRegularUserUsername(),
				follow.getProfile().getRegularUserUsername());
		notificationService.createSettings(follow.getId(), follow.getProfile().getRegularUserUsername());*/

		return deleteRequest(username, profile.getAccountName());
	}




	@Override
	public Set<FollowRequestDTO> deleteRequest(String username, String loggedInUsername) {
		
		Set<FollowRequest> requests = followRequestRepo.findByUser(userRepository.findOneByUsername(loggedInUsername));

		Set<FollowRequestDTO> followRequests = new HashSet<FollowRequestDTO>();

		FollowRequest delete = new FollowRequest();
		for (FollowRequest f : requests) {
			if (f.getRequester().getUsername().equals(username)) {
				delete = f;
				break;
			}
		}
		if (delete != null) {
			followRequestRepo.delete(delete);
		}
		Set<FollowRequest> newRequests = followRequestRepo
				.findByUser(userRepository.findOneByUsername(loggedInUsername));

		for (FollowRequest f : newRequests) {
			FollowRequestDTO dto = new FollowRequestDTO();
			dto.setUsername1(f.getUser().getUsername());
			dto.setUsername2(f.getRequester().getUsername());
			dto.setId(f.getId());
			followRequests.add(dto);
		}

		return followRequests;
	}


	@Override
	public UrlResource getContent(String contentName) throws MalformedURLException {
		return new UrlResource("file:" + storageDirectoryPath + File.separator + contentName);
	}

	@Override
	public List<FollowerDTO> getFollowing(String username) {
		List<FollowerDTO> following = new ArrayList<FollowerDTO>();
		for (Follow f : followRepository.findAll().stream().filter(f -> !f.isBlocked() && f.getUser().isActive()).collect(Collectors.toList())) {
			if (f.getFollower().getAccountName().equals(username)) {
				following.add(new FollowerDTO(f.getUser().getAccountName()));
			}
		}
		return following;
	}



	@Override
	public User get(String username) {
		User u = userRepository.findByUsername(username);
		if (u == null)
			throw new IllegalArgumentException("Profile with username " + username + " not found");
		return u;
	}



	@Override
	public String addCloseFriend(String myUsername, String usernameOfFriend) {
		for (Follow f : followRepository.findAll()) {
			System.out.println("Ovo je close " + f.isCloseFriend());
			System.out.println(f.getUser().getUsername());
			System.out.println(myUsername);
			System.out.println(f.getFollower().getUsername());
			System.out.println(usernameOfFriend.trim());
			if (f.getUser().getUsername().equals(myUsername)
					&& f.getFollower().getUsername().equals(usernameOfFriend.trim())
					&& f.isCloseFriend() == true) {
				return "You have already choose this profile as your close friend.";
			} else if (f.getUser().getUsername().equals(myUsername)
					&& f.getFollower().getUsername().equals(usernameOfFriend.trim())
					&& f.isCloseFriend() == false) {
				f.setCloseFriend(true);
				followRepository.save(f);
				return "You have successfully added your close friend";
			}
		}
		return "This profile can't be set as your close friend";

	}



	@Override
	public String removeCloseFriend(String myUsername, String usernameOfFriend) {
		for (Follow f : followRepository.findAll()) {
			if (f.getUser().getUsername().equals(myUsername)
					&& f.getFollower().getUsername().equals(usernameOfFriend.trim())
					&& f.isCloseFriend() == false) {
				return "You have already removed this profile from your close friends.";
			} else if (f.getUser().getUsername().equals(myUsername)
					&& f.getFollower().getUsername().equals(usernameOfFriend.trim())
					&& f.isCloseFriend() == true) {
				f.setCloseFriend(false);
				followRepository.save(f);
				return "You have successfully removed your close friend";
			}
		}
		return "This profile can't be removed from your close friends list";
	}



	@Override

	public ProfileInfoDTO getProfile(String requestedBy, String username) throws ProfileNotFoundException {
		
		User user = userRepository.findOneByAccountName(username);
		
		if(user == null) {
			throw new ProfileNotFoundException();
		}
		
		ProfileInfoDTO result = new ProfileInfoDTO();
		
		
		result.setId(user.getId());
		result.setUsername(user.getAccountName());
		result.setBio(user.getBiography());
		//result.setBirthDate();
		result.setEmail(user.getUsername());
		result.setFullName(user.getFirstName() + " " +user.getLastName());
		result.setGender(user.getGender().toString());
		result.setWebsite(user.getWebsiteLink());
		result.setFollowerCount(getFollowerCount(user));
		result.setFollowingCount(getFollowingCount(user));
		result.setOwned(user.getUsername().equals(requestedBy) ? true : false);
		result.setPrivateProfile(!user.isPublic());
		result.setFollowing(isFollowing(user, requestedBy));
		result.setVerified(user.isVerified());
		
		
		
		return result;
		
	}
	
	
	private int getFollowerCount(User user) {
		return followRepository.findByUser(user).stream().filter(f-> !f.isBlocked() && f.getFollower().isActive()).collect(Collectors.toList()).size();
	}

	private int getFollowingCount(User user) {
		return followRepository.findByFollower(user).stream().filter(f-> !f.isBlocked() && f.getUser().isActive()).collect(Collectors.toList()).size();
	}

	private boolean isFollowing(User user, String follower) {
		return followRepository.findAll().stream()
				.filter(f -> f.getUser().getUsername().equals(user.getUsername())
						&& f.getFollower().getUsername().equals(follower))
				.count() > 0;
	}



	@Override
	public User getUserFromUsername(String username) {

		return userRepository.findOneByUsername(username);
	}



	@Override
	public String getUsernameFromAccountName(String accountName) {
		
		return userRepository.findOneByAccountName(accountName).getUsername();
	}









}