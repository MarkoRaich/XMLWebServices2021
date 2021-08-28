package ftn.xws.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.xws.userservice.service.FollowService;

@RestController
@RequestMapping(value = "/follow", produces = MediaType.APPLICATION_JSON_VALUE)
public class FollowController {
	
	@Autowired
    private FollowService followService;

    

    @GetMapping(value = "/followedBy/{followId}/", produces = "application/json")
    public String getFollowedBy(@PathVariable("followId") long followId) {
        return followService.getFollowedBy(followId);
    }
}
