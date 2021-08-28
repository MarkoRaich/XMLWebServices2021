package ftn.xws.contentservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ftn.xws.contentservice.dto.StoryInfoDTO;
import ftn.xws.contentservice.exception.ProfileBlockedException;
import ftn.xws.contentservice.exception.ProfilePrivateException;
import ftn.xws.contentservice.service.StoryService;
import ftn.xws.contentservice.service.UserService;
import ftn.xws.contentservice.util.TokenUtils;

@RestController
@RequestMapping(value = "/story")
public class StoryController {
	
	@Autowired
	private StoryService storyService;
	
	@Autowired
    private UserService userService;



    @GetMapping(value = "feed")
    public ResponseEntity<List<StoryInfoDTO>> getFeed(@RequestHeader("Authorization") String auth) {
        if(!userService.verify(auth, "USER"))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        String username = userService.getUsernameFromToken(TokenUtils.getToken(auth));
        return new ResponseEntity<>(storyService.getFeed(username), HttpStatus.OK);
    }
    
    @GetMapping(value = "/profile/{username}")
    public ResponseEntity<List<StoryInfoDTO>> getForProfile(@PathVariable("username") String profile, @RequestHeader("Authorization") String auth) {
    	
        try {
            if(userService.verify(auth, "USER")) {
                String username = userService.getUsernameFromToken(TokenUtils.getToken(auth));
                return new ResponseEntity<>(storyService.getForProfile(username, profile), HttpStatus.OK);
            }
            else if (auth.equals("Bearer null"))
                return new ResponseEntity<>(storyService.getForProfile(null, profile), HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (ProfilePrivateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile is private");
        } catch (ProfileBlockedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You blocked this profile");
        }
    }
    
    @GetMapping(value = "allStories")
    public ResponseEntity<List<StoryInfoDTO>> getAllStories(@RequestHeader("Authorization") String auth) {
        if(userService.verify(auth, "USER")) {
            String username = userService.getUsernameFromToken(TokenUtils.getToken(auth));
            return new ResponseEntity<>(storyService.getAllUserStories(username), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    
    @GetMapping(value = "storyHighlights")
    public ResponseEntity<List<StoryInfoDTO>> getAllHighlights(@RequestHeader("Authorization") String auth) {
    	
        if(userService.verify(auth, "USER")) {
            String username = userService.getUsernameFromToken(TokenUtils.getToken(auth));
            return new ResponseEntity<>(storyService.getStoryHighlights(username), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    
    @PostMapping(value = "saveToHighlights")
    public ResponseEntity<String> addToHighlights(@RequestBody StoryInfoDTO dto ,@RequestHeader("Authorization") String auth) {
        if(userService.verify(auth, "USER")) {
            storyService.addToStoryHighlights(dto);
            return new ResponseEntity<>("Successfully saved to story highlights", HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
