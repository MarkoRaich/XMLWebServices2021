package ftn.xws.notificationservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.xws.notificationservice.model.NotificationSettingsPerFollow;
import ftn.xws.notificationservice.model.NotificationSettingsPerProfile;
import ftn.xws.notificationservice.repository.SettingsPerFollowRepository;
import ftn.xws.notificationservice.repository.SettingsPerProfileRepository;
import ftn.xws.notificationservice.service.AuthService;
import ftn.xws.notificationservice.service.SettingsService;
import ftn.xws.notificationservice.util.TokenUtils;

@RestController
@RequestMapping(value = "/settings")
public class SettingsController {
	
	@Autowired
	private SettingsService settingsService;
	//@Autowired
	//private AuthService authService;

	
	@GetMapping(value = "/profile/")
    public NotificationSettingsPerProfile getForProfile(/*@RequestHeader("Authorization") String auth*/) {
        /*if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT"))
            return null;
        String token = TokenUtils.getToken(auth);*/
        String username = "mare";//authService.getUsernameFromToken(token);
        return settingsService.get(username);
    }
	
	@GetMapping(value = "/follow/")
    public List<NotificationSettingsPerFollow> getForFollow(@RequestHeader("Authorization") String auth) {
       /* if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT"))
            return null;
        String token = TokenUtils.getToken(auth);*/
        String username = "mare";//authService.getUsernameFromToken(token);
        return settingsService.getByProfile(username);
    }
	
	@PutMapping(value = "/profile/")
    public void update(@RequestBody NotificationSettingsPerProfile settings/*, @RequestHeader("Authorization") String auth*/) {
        /*if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT"))
            return;
        String token = TokenUtils.getToken(auth);*/
        String username = "mare";//authService.getUsernameFromToken(token);
        settingsService.update(settings, username);
    }
	
	@PutMapping(value = "/follow/")
    public void update(@RequestBody NotificationSettingsPerFollow settings/*, @RequestHeader("Authorization") String auth*/) {
        /*if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT"))
            return;
        String token = TokenUtils.getToken(auth);*/
        String username = "mare";//authService.getUsernameFromToken(token);
        settingsService.update(settings, username);
    }
	
	@PostMapping(value = "/profile/{username}/")
    public void create(@PathVariable("username") String username) {
        settingsService.createSettingsPerProfile(username);
    }
	
	@PostMapping(value = "/follow/{followId}/{profile}/")
    public void create(@PathVariable("followId") long followId, @PathVariable("profile") String profile) {
        settingsService.createSettingsPerFollow(followId, profile);
    }
	
	@DeleteMapping(value = "/follow/{followId}/")
    public void delete(@PathVariable("followId") long followId) {
        settingsService.deleteSettingsPerFollow(followId);
    }
	
	
}
