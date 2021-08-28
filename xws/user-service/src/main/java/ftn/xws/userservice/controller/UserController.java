package ftn.xws.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ftn.xws.userservice.dto.FollowRequestDTO;
import ftn.xws.userservice.dto.FollowerDTO;
import ftn.xws.userservice.dto.UsernameDTO;
import ftn.xws.userservice.dto.ProfileInfoDTO;
import ftn.xws.userservice.dto.UserInfoDTO;
import ftn.xws.userservice.exception.ProfileNotFoundException;
import ftn.xws.userservice.model.User;
import ftn.xws.userservice.security.TokenUtils;
import ftn.xws.userservice.service.AuthenticationService;
import ftn.xws.userservice.service.UserService;
import ftn.xws.userservice.utils.TokenUtilss;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateEncodingException;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;



@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

		@Autowired
		private UserService userService;
		
		@Autowired
		private AuthenticationService authService;
		
		@Autowired
		private TokenUtils tokenUtils;
		
		
		
		
		
		@GetMapping(value="/my-info", produces="application/json")
	    public ResponseEntity<UserInfoDTO> getMyCertificate(@RequestHeader("Authorization") String auth) throws CertificateEncodingException {

//	        User user = userService.getLoginUser();
//	        if(user == null) {
//	        	return null;
//	        }
			
			String username;
			String token = tokenUtils.getToken(auth);
			if(token!=null) {
				username = tokenUtils.getUsernameFromToken(token);
				User user = userService.getUserFromUsername(username);
				
				if(user == null) {
					return null;
				}
				
			return new ResponseEntity<>(new UserInfoDTO(user),HttpStatus.OK);
			
			}
			
			return null;
			  
	    }

		@PostMapping(value = "/myAccountName", consumes= MediaType.APPLICATION_JSON_VALUE )
		public ResponseEntity<String> getMyAccountName(@Valid @RequestBody UsernameDTO usernameDTO) {
			return new ResponseEntity<>(userService.getAccountName( usernameDTO.getUsername() ), HttpStatus.OK);
		}
		
		
		@PostMapping(value = "/change-info", consumes = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Boolean> changeInfo(@Valid @RequestBody UserInfoDTO userDTO) {

			User user = userService.changeUsersInfo(userDTO);
		        if (user == null) {
		            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
		        }
		        
		        System.out.println("Izmenjeni korisnikovi podaci: " + user.getAccountName());
		        return new ResponseEntity<>(true, HttpStatus.CREATED);
		}

		
				
		@GetMapping(value = "/follow/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Integer> followProfile(@RequestHeader("Authorization") String auth ,@PathVariable String username){
			if(!authService.verify(auth, "USER")) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			String token = TokenUtilss.getToken(auth);
			String loggedInUsername = authService.getUsernameFromToken(token);
			int followerCount = userService.followProfile(username, loggedInUsername); 
			return new ResponseEntity<>(followerCount, HttpStatus.OK);
		}
		
		@GetMapping(value = "/unfollow/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Integer> unfollowProfile(@RequestHeader("Authorization") String auth, @PathVariable String username){
			
			if(!authService.verify(auth, "USER"))  {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			
			String loggedInUsername = authService.getUsernameFromToken(TokenUtilss.getToken(auth));
			int followerCount = userService.unfollowProfile(username, loggedInUsername); 
			return new ResponseEntity<>(followerCount, HttpStatus.OK);
		}
		
		@GetMapping(value = "/followRequest/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Set<FollowRequestDTO>> getFollowRequests(@RequestHeader("Authorization") String auth, @PathVariable String username){
			if(!authService.verify(auth, "USER")) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			Set<FollowRequestDTO> requests = userService.getFollowRequests(username);
			return new ResponseEntity<>(requests, HttpStatus.OK);
		}
		
		@GetMapping(value = "/acceptRequest/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Set<FollowRequestDTO>> acceptRequest(@RequestHeader("Authorization") String auth, @PathVariable String username){
			if(!authService.verify(auth, "USER")) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			String loggedInUsername = authService.getUsernameFromToken(TokenUtilss.getToken(auth));
			Set<FollowRequestDTO> requests = userService.acceptRequest(username, loggedInUsername);
			return new ResponseEntity<>(requests, HttpStatus.OK);
		}
		
		@GetMapping(value = "/deleteRequest/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Set<FollowRequestDTO>> deleteRequest(@RequestHeader("Authorization") String auth, @PathVariable String username){
			if(!authService.verify(auth, "USER")) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			String loggedInUsername = authService.getUsernameFromToken(TokenUtilss.getToken(auth));
			Set<FollowRequestDTO> requests = userService.deleteRequest(username, loggedInUsername);
			return new ResponseEntity<>(requests, HttpStatus.OK);
		}
		
		@GetMapping(value = "/content/{contentName:.+}/")
		public @ResponseBody ResponseEntity<UrlResource> getContent(@PathVariable(name = "contentName") String fileName) {
			try {
				UrlResource resource = userService.getContent(fileName);
				return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
						.contentType(MediaTypeFactory
								.getMediaType(resource)
								.orElse(MediaType.APPLICATION_OCTET_STREAM))
								.body(this.userService.getContent(fileName));
			} catch (IOException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
			}
		}
		
		@GetMapping(value = "/following/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<FollowerDTO>> getFollowing(@RequestHeader("Authorization") String auth, @PathVariable String username){
			if(!authService.verify(auth, "USER")) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			
			List<FollowerDTO> following = userService.getFollowing(username);
			return new ResponseEntity<>(following, HttpStatus.OK);
		}
		
		@GetMapping(value = "/followers/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<FollowerDTO>> getFollowers(@RequestHeader("Authorization") String auth, @PathVariable String username){
			if(!authService.verify(auth, "USER")) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}

			List<FollowerDTO> followers = userService.getFollowers(username);
			return new ResponseEntity<>(followers, HttpStatus.OK);
		}

		@GetMapping(value = "/getInfo/{username}")
		public ResponseEntity<ProfileInfoDTO> getInfo(@PathVariable String username, @RequestHeader("Authorization") String auth) {
			
			try {
				String requestedBy = null;
				if (!auth.equals("Bearer null")) {
					requestedBy = tokenUtils.getUsernameFromToken(auth.substring(7));
					System.out.println(requestedBy);
				}
				return new ResponseEntity<>(userService.getProfile(requestedBy, username), HttpStatus.OK);
			} catch (ProfileNotFoundException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			}
		}
		
		@GetMapping(value = "/one/{username}/")
		public ResponseEntity<User> get(@RequestHeader("Authorization") String auth, @PathVariable String username) {
			if(!authService.verify(auth, "USER")) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}

			try {
				User profile = userService.get(username);
				return new ResponseEntity<>(profile, HttpStatus.OK);
			} catch (IllegalArgumentException e) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		
		@PostMapping(value = "/addCloseFriend")
		public ResponseEntity<String> addCloseFriend(@RequestBody String usernameOfFriend,  @RequestHeader("Authorization") String auth)
		{
			if(!authService.verify(auth, "USER"))
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			String token = TokenUtilss.getToken(auth);
			String myUsername = authService.getUsernameFromToken(token);

			String message = userService.addCloseFriend(myUsername, usernameOfFriend);
			
			return new ResponseEntity<>(message, HttpStatus.OK);
		}
		
		@PostMapping(value = "/removeCloseFriend")
		public ResponseEntity<String> removeCloseFriend(@RequestBody String usernameOfFriend,  @RequestHeader("Authorization") String auth)
		{
			if(!authService.verify(auth, "USER"))
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			String token = TokenUtilss.getToken(auth);
			String myUsername = authService.getUsernameFromToken(token);

			String message = userService.removeCloseFriend(myUsername, usernameOfFriend);
			
			return new ResponseEntity<>(message, HttpStatus.OK);
		}
		
		@GetMapping(value = "getCloseFriends")
		public ResponseEntity<List<String>> getCloseFriendsForProfile(@RequestHeader("Authorization") String auth) {
			if(!authService.verify(auth, "USER"))
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			String token = TokenUtilss.getToken(auth);
			String myUsername = authService.getUsernameFromToken(token);
			
			List<String> close = userService.getCloseFriends(myUsername);
			return new ResponseEntity<>(close, HttpStatus.OK);
		}
		

		//don't put in api gateway
		@GetMapping(value = "ms")
		public List<String> getAll() {
			return userService.getAll(); 
		}
		
		@GetMapping("ms/getUsernameFromAccountName/{accountName}")
		public String getUsernameFromAccountName(@PathVariable String accountName) {
			return userService.getUsernameFromAccountName(accountName);
		}

		//don't put in api gateway
		@GetMapping(value = "ms/followers/{profile}")
		public List<String> getFollowerss(@PathVariable String profile) {
			return userService.getFollowerss(profile);
		}

		//don't put in api gateway
		@GetMapping(value = "ms/following/{profile}")
		public List<String> getFollowingg(@PathVariable String profile) {
			return userService.getFollowingg(profile);
		}

		//don't put in api gateway
		@GetMapping(value = "ms/muted/{profile}")
		public List<String> getMuted(@PathVariable String profile) {
			return userService.getMuted(profile);
		}

		//don't put in api gateway
		@GetMapping(value = "ms/blocked/{profile}/")
		public List<String> getBlocked(@PathVariable String profile) throws UnsupportedEncodingException {
			
			return userService.getBlocked(profile);
		}

		//don't put in api gateway
		@GetMapping(value = "ms/close/{profile}")
		public List<String> getCloseFriends(@PathVariable String profile) {
			return userService.getCloseFriends(profile);
		}

		//don't put in api gateway
		@GetMapping(value = "ms/public/{profile}")
		public boolean isPublic(@PathVariable String profile) throws Exception {
			try {
				return userService.isPublic(profile);
			} catch (ProfileNotFoundException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			}
		}
		

		//don't put in api gateway
			@GetMapping(value = "ms/deactivate/{username}")
			public void deactivateProfile(@PathVariable String username) {
				userService.deactivateProfile(username);
			}
			
		//don't put in api gateway
			@GetMapping(value = "ms/getAllInactiveProfiles")
			public List<String> getAllInactiveProfiles() {
		    	return userService.getAllInactiveProfiles();
		}	


	
}
