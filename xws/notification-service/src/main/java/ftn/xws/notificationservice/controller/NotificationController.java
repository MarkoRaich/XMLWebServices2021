package ftn.xws.notificationservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.xws.notificationservice.dto.NewNotificationDTO;
import ftn.xws.notificationservice.dto.NotificationDTO;
import ftn.xws.notificationservice.service.NotificationService;
import ftn.xws.notificationservice.service.AuthService;
import ftn.xws.notificationservice.util.TokenUtils;

@RestController
@RequestMapping(value = "/notification")
public class NotificationController {
	
	@Autowired
	private NotificationService notificationService;
	/*@Autowired
	private AuthService userService;*/

    
    

    @GetMapping
    public List<NotificationDTO> getAll(/*@RequestHeader("Authorization") String auth*/) {
        /*if(!userService.verify(auth, "USER") && !userService.verify(auth, "AGENT"))
            return null;
        String token = TokenUtils.getToken(auth);*/
        String username = "mare";//userService.getUsernameFromToken(token);
        return notificationService.getAll(username);
    }
    
    
    @PostMapping
    public void create(@RequestBody NewNotificationDTO dto) {
        this.notificationService.create(dto);
    }
}
