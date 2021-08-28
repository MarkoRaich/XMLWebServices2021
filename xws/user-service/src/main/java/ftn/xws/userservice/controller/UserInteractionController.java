package ftn.xws.userservice.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.xws.userservice.service.UserInteractionService;
import ftn.xws.userservice.service.UserService;


@RestController
@RequestMapping(value = "/interaction", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserInteractionController {


	@Autowired
	private final UserInteractionService service;

    @Autowired
	private final UserService userService;
	
	public UserInteractionController(UserInteractionService service, UserService userService) {
		super();
		this.service = service;
		this.userService = userService;
	}

	@GetMapping(value = "/block-unblock/{blocked}/{blockedBy}/")
    public ResponseEntity<Void> updateBlock(@RequestHeader("Authorization") String auth, @PathVariable String blocked, @PathVariable String blockedBy) {
       /* if(!userService.verify(auth, "USER"))) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }*/

        service.updateBlock(blocked, blockedBy);
        return new ResponseEntity<>(HttpStatus.OK);
    }
   
	@GetMapping(value = "/mute-unmute/{muted}/{mutedBy}/")
    public ResponseEntity<Void> updateMute(@RequestHeader("Authorization") String auth, @PathVariable String muted, @PathVariable String mutedBy) {
        /*if(!userService.verify(auth, "USER")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }*/

        try {
            service.updateMute(muted, mutedBy);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/muted/{username}/")
    public ResponseEntity<Collection<String>> getMuted(@RequestHeader("Authorization") String auth, @PathVariable String username) {
        /*if(!userService.verify(auth, "USER")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }*/

        return new ResponseEntity<>(service.getMutedProfiles(username), HttpStatus.OK);
    }

    @GetMapping(value = "/blocked/{username}/")
    public ResponseEntity<Collection<String>> getBlocked(@RequestHeader("Authorization") String auth, @PathVariable String username) {
        /*if(!userService.verify(auth, "USER")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }*/

        return new ResponseEntity<>(service.getBlockedProfiles(username), HttpStatus.OK);
    }

}
