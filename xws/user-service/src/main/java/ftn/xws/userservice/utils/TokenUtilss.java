package ftn.xws.userservice.utils;

import org.springframework.stereotype.Component;

@Component
public class TokenUtilss {

    public static String getToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
