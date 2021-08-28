package ftn.xws.userservice.service;

import java.util.Collection;

public interface UserInteractionService {
	
    void updateMute(String muted, String mutedBy);
    
    void updateBlock(String blocked, String blockedBy);
    
    Collection<String> getMutedProfiles(String username);
    
    Collection<String> getBlockedProfiles(String username);

}
