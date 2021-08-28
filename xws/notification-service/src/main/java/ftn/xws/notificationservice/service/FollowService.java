package ftn.xws.notificationservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "follow", url = "${app.user.url}")
public interface FollowService {
    @GetMapping(value = "/follow/followedBy/{followId}")
    String getProfileByFollow(@PathVariable("followId") long followId);
}
