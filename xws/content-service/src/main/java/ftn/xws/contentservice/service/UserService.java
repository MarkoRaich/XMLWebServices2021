package ftn.xws.contentservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ftn.xws.contentservice.dto.UsernameDTO;

import java.util.List;

@FeignClient(name = "user", url = "${app.user.url}")
public interface UserService {
    
    @PostMapping("/auth/{token}/{role}")
    boolean verify(@PathVariable("token") String token, @PathVariable("role") String role);
   
    @GetMapping("/auth/{token}/")
    String getUsernameFromToken(@PathVariable("token") String token);
    
    
    
    @GetMapping("user/ms")
    List<String> getAll();
    
    @GetMapping("user/ms/getUsernameFromAccountName/{accountName}")
    String getUsernameForAccountName(@PathVariable("accountName") String accountName);
    
    @PostMapping("user/myAccountName")
    ResponseEntity<String> getAccountName(@RequestBody UsernameDTO usernameDTO);
    
    
    @GetMapping("user/ms/followers/{profile}")
    List<String> getFollowers(@PathVariable("profile") String profile);
    
    @GetMapping("user/ms/following/{profile}")
    List<String> getFollowing(@PathVariable("profile") String profile);
    
    @GetMapping("user/ms/muted/{profile}")
    List<String> getMuted(@PathVariable("profile") String profile);
    
    @GetMapping("user/ms/blocked/{profile}/")
    List<String> getBlocked(@PathVariable("profile") String profile);
    
    @GetMapping("user/ms/close/{profile}")
    List<String> getCloseFriends(@PathVariable("profile") String profile);
    
    @GetMapping("user/ms/public/{profile}")
    boolean isPublic(@PathVariable("profile") String profile);
    
    
    @GetMapping("user/ms/deactivate/{username}")
    void deactivateProfile(@PathVariable("username") String username);
    
    @GetMapping("user/ms/getAllInactiveProfiles")
    List<String> getAllInactiveProfiles();


    
    
}

