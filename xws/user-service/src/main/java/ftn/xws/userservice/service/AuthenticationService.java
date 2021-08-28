package ftn.xws.userservice.service;

import javax.validation.Valid;

import ftn.xws.userservice.dto.LoggedInUserDTO;
import ftn.xws.userservice.dto.UserDTO;
import ftn.xws.userservice.security.auth.JwtAuthenticationRequest;

public interface AuthenticationService {

	LoggedInUserDTO login(@Valid JwtAuthenticationRequest authenticationRequest);

	UserDTO registerUser(@Valid UserDTO userDTO);

	Boolean verify(String token, String role);

	String getUsernameFromToken(String token);

}
