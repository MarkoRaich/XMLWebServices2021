package ftn.xws.contentservice.service;

import java.util.List;

import ftn.xws.contentservice.dto.StoryInfoDTO;
import ftn.xws.contentservice.exception.ProfileBlockedException;
import ftn.xws.contentservice.exception.ProfilePrivateException;

public interface StoryService {
	
	List<StoryInfoDTO> getFeed(String username);

    List<StoryInfoDTO> getForProfile(String requestedBy, String profile) throws ProfilePrivateException, ProfileBlockedException;
    
    List<StoryInfoDTO> getAllUserStories(String username);
    
    List<StoryInfoDTO> getStoryHighlights(String username);
    
    void addToStoryHighlights(StoryInfoDTO dto);

}
