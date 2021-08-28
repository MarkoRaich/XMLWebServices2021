package ftn.xws.userservice.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.xws.userservice.dto.LoggedInUserDTO;
import ftn.xws.userservice.dto.UserDTO;
import ftn.xws.userservice.security.auth.JwtAuthenticationRequest;
import ftn.xws.userservice.service.AuthenticationService;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;


@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	
	@Autowired
    private AuthenticationService authService;
	
	
	
	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> registerUser(@Valid @RequestBody UserDTO userDTO) {

		UserDTO user = authService.registerUser(userDTO);
	        if (user == null) {
	            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
	        }
	        
	        System.out.println("Registrovan novi korisnik: " + user.getAccountName());
	        return new ResponseEntity<>(true, HttpStatus.CREATED);
	}
	
	
	@PostMapping(value= "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LoggedInUserDTO> login(@Valid @RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException, IOException {
		 
		 try {
	        	
	            LoggedInUserDTO loggedInUserDTO = authService.login(authenticationRequest);

	            if (loggedInUserDTO == null) {
	                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	            } else {
	            	System.out.println("Ulogovan " + loggedInUserDTO.getRole() + " : " + loggedInUserDTO.getUsername());
	                return new ResponseEntity<>(loggedInUserDTO, HttpStatus.OK);
	            }
	        } catch (AuthenticationException e) {
	            e.printStackTrace();
	        }
	        
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
	}

    @PostMapping(value= "/{token}/{role}")
    public ResponseEntity<Boolean> verify(@PathVariable("token") String token, @PathVariable("role") String role) {
    	
    	//System.out.println(token + " , " + role );
        return new ResponseEntity<>(authService.verify(token, role), HttpStatus.OK);
    }

    @GetMapping(value= "/{token}/")
    public ResponseEntity<String> getUsernameFromToken(@PathVariable("token") String token) {
    	//System.out.println(token);
        return new ResponseEntity<>(authService.getUsernameFromToken(token), HttpStatus.OK);
    }
	

}
