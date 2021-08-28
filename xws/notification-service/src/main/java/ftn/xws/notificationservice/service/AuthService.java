package ftn.xws.notificationservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "auth", url = "${app.user.url}")
public interface AuthService {
    @PostMapping("/auth/{token}/{role}")
    boolean verify(@PathVariable("token") String token, @PathVariable("role") String role);
    @GetMapping("/auth/{token}")
    String getUsernameFromToken(@PathVariable("token") String token);
}
