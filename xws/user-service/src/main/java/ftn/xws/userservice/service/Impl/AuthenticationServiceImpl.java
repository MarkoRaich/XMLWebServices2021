package ftn.xws.userservice.service.Impl;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ftn.xws.userservice.dto.LoggedInUserDTO;
import ftn.xws.userservice.dto.UserDTO;
import ftn.xws.userservice.dto.UserTokenState;
import ftn.xws.userservice.model.Admin;
import ftn.xws.userservice.model.Authority;
import ftn.xws.userservice.model.User;
import ftn.xws.userservice.repository.AuthorityRepository;
import ftn.xws.userservice.repository.UserRepository;
import ftn.xws.userservice.security.TokenUtils;
import ftn.xws.userservice.security.auth.JwtAuthenticationRequest;
import ftn.xws.userservice.service.AuthenticationService;
import ftn.xws.userservice.service.UserService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	 @Autowired
	 private AuthenticationManager authManager;
	 
	 @Autowired
	 private PasswordEncoder passwordEncoder;
	 
	 @Autowired
	 private AuthorityRepository authRepository;
	 
	 @Autowired
	 private UserRepository userRepository;
	 
	 @Autowired
	 private TokenUtils tokenUtils;
	
	 @Autowired
	 private UserService userService;
	 
	
	@Override
	public LoggedInUserDTO login(@Valid JwtAuthenticationRequest jwtAuthenticationRequest) {

			final Authentication authentication = authManager.authenticate(
	            new UsernamePasswordAuthenticationToken(
	                jwtAuthenticationRequest.getUsername(),
	                jwtAuthenticationRequest.getPassword()
	            )
	        );
	        
	        SecurityContextHolder.getContext().setAuthentication(authentication);

	        //kreiranje tokena za korisnika
	        String username = returnUsername(authentication.getPrincipal());
	        if (username == null) {
	            return null;
	        }
	        	        
	        String jwtToken = tokenUtils.generateToken(username);
	        int expiresIn = tokenUtils.getExpiredIn();
	        
	        //System.out.println(jwtToken);
	        return returnLoggedInUser(
	            authentication.getPrincipal(),
	            new UserTokenState(jwtToken, expiresIn)
	        );
		
		
	}



	@Override
	public UserDTO registerUser(@Valid UserDTO userDTO) {


		//provera da li postoji nalog sa tim email-om
		if(userService.emailIsUsed(userDTO.getEmail())) {
			
			System.out.println("nadjen isti email....");
			return null;
		}
		
		//provera da li postoji nalog sa tim email-om
		if(userService.accountNameIsUsed(userDTO.getAccountName())) {
			
			System.out.println("nadjen isti account name....");
			return null;
		}

		
		User newUser = new User(userDTO);
		
		
		newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		newUser.setAuthorities(findByName("ROLE_USER"));
		

		return new UserDTO(userRepository.save(newUser));
				
		
		
	}
	
	
	
    public Set<Authority> findByName(String name) {
        Authority auth = this.authRepository.findByName(name);
        Set<Authority> authorities = new HashSet<>();
        authorities.add(auth);
        return authorities;
    }



	private String returnUsername(Object object) {
	       
	       if (object instanceof Admin) {
	            return ((Admin) object).getUsername();
	        } else if (object instanceof User) {
	            return ((User) object).getUsername();
	        } 
	       
	        return null;
	    }
	    

	    private LoggedInUserDTO returnLoggedInUser(Object object, UserTokenState userTokenState) {
	        if (object instanceof Admin) {
	        	Admin admin = (Admin) object;
	            return new LoggedInUserDTO(admin.getId(), admin.getUsername(), "ADMIN", userTokenState); 
	        } else if (object instanceof User
	        		
	        		
	        		) {
	        	User entity = (User) object;
	        	return new LoggedInUserDTO(entity.getId(), entity.getUsername(), "USER", userTokenState);
	        }

	        return null;
	 
	    }



		@Override
		public Boolean verify(String header, String role) {
			
			return tokenUtils.isTokenVerified(header, role);
			
		}



		@Override
		public String getUsernameFromToken(String token) {
			
			return tokenUtils.getUsernameFromToken(token);
		}


}
