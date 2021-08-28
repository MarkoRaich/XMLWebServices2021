package ftn.xws.contentservice.util;

import org.springframework.stereotype.Component;

@Component
public class TokenUtils {

    public static String getToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
