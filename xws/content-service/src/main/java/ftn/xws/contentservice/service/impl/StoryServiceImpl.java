package ftn.xws.contentservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ftn.xws.contentservice.dto.StoryInfoDTO;
import ftn.xws.contentservice.dto.UsernameDTO;
import ftn.xws.contentservice.exception.ProfileBlockedException;
import ftn.xws.contentservice.exception.ProfilePrivateException;
import ftn.xws.contentservice.model.Story;
import ftn.xws.contentservice.repository.StoryRepository;
import ftn.xws.contentservice.service.StoryService;
import ftn.xws.contentservice.service.UserService;

@Service
public class StoryServiceImpl implements StoryService {
	
	@Autowired
	private StoryRepository storyRepository;
	
	@Autowired
	private UserService userService;



	@Override
	public List<StoryInfoDTO> getFeed(String username) {
		List<StoryInfoDTO> result = new ArrayList<>();

		List<String> targetedProfiles = userService.getFollowing(username);
		List<String> mutedProfiles = userService.getMuted(username);
		List<String> blockedProfiles = userService.getBlocked(username);
		List<String> inactiveProfiles = userService.getAllInactiveProfiles();
		
		targetedProfiles.removeAll(mutedProfiles);
		targetedProfiles.removeAll(blockedProfiles);
		targetedProfiles.removeAll(inactiveProfiles);

		List<Story> targetedStories = storyRepository.findAll().stream()
				.filter(s -> targetedProfiles.contains(s.getMedia().getUsername())).collect(Collectors.toList());

		for (Story story : targetedStories) {
			if (story.isActive() && (!story.isCloseFriends()
					|| userService.getCloseFriends(story.getMedia().getUsername()).contains(username)))
				story.getMedia().getPath().forEach(url -> result.add(toStoryInfoDTO(story, url)));
		}
		storyRepository.findAll().stream().filter(s -> s.getMedia().getUsername().equals(username) && s.isActive())
				.forEach(s -> s.getMedia().getPath().forEach(url -> result.add(toStoryInfoDTO(s, url))));

		result.sort((r1, r2) -> r1.getCreated().isBefore(r2.getCreated()) ? 1 : -1);
		return result;
	}

	@Override
	public List<StoryInfoDTO> getForProfile(String requestedBy, String profile) throws ProfilePrivateException, ProfileBlockedException {
		
		List<StoryInfoDTO> result = new ArrayList<>();

		List<String> followers = userService.getFollowing(profile);
		
		UsernameDTO usernameDTO = new UsernameDTO();
		usernameDTO.setUsername(requestedBy);
		
		System.out.println(requestedBy);
		
		ResponseEntity<String> response;
		boolean follower;
		
		if(requestedBy!=null) {
			response =  userService.getAccountName(usernameDTO);
			follower = followers.contains(response.getBody());
		} else {
			response = new ResponseEntity<>("nijeDjidji", HttpStatus.OK);
			follower = false;
		}
		
		
		
		if (!userService.isPublic(profile) && !follower && !profile.equals(response.getBody()))
			throw new ProfilePrivateException();

		if (requestedBy != null) {
			List<String> blockedProfiles = userService.getBlocked(requestedBy);
			boolean blocked = blockedProfiles.contains(profile);
			if (blocked)
				throw new ProfileBlockedException();
		}

		List<Story> stories = storyRepository.findAll().stream()
				.filter(p -> p.getMedia().getUsername().equals(profile) && p.isActive()
						&& (!p.isCloseFriends() || profile.equals(requestedBy)
								|| userService.getCloseFriends(profile).contains(requestedBy)))
				.collect(Collectors.toList());
		stories.forEach(s -> s.getMedia().getPath().forEach(url -> result.add(toStoryInfoDTO(s, url))));

		result.sort((r1, r2) -> r1.getCreated().isBefore(r2.getCreated()) ? 1 : -1);
		return result;
	}

	@Override
	public List<StoryInfoDTO> getAllUserStories(String username) {
		
		UsernameDTO usernameDTO = new UsernameDTO();
		usernameDTO.setUsername(username);
	
		ResponseEntity<String> response =  userService.getAccountName(usernameDTO);
		String accountName = response.getBody();
	

		List<StoryInfoDTO> result = new ArrayList<StoryInfoDTO>();
		List<Story> stories = storyRepository.findAll().stream()
				.filter(s -> s.getMedia().getUsername().equals(accountName)).collect(Collectors.toList());
		stories.forEach(s -> s.getMedia().getPath().forEach(url -> result.add(toStoryInfoDTO(s, url))));
		return result;
	}

	@Override
	public List<StoryInfoDTO> getStoryHighlights(String username) {
		
		UsernameDTO usernameDTO = new UsernameDTO();
		usernameDTO.setUsername(username);
	
		ResponseEntity<String> response =  userService.getAccountName(usernameDTO);
		String accountName = response.getBody();
		
		List<StoryInfoDTO> result = new ArrayList<StoryInfoDTO>();
		List<Story> stories = storyRepository.findAll().stream()
				.filter(s -> s.getMedia().getUsername().equals(accountName) && s.isHighlighted()).collect(Collectors.toList());
		stories.forEach(s -> s.getMedia().getPath().forEach(url -> result.add(toStoryInfoDTO(s, url))));
		return result;
	}
	
	@Override
	public void addToStoryHighlights(StoryInfoDTO dto) {
		Story story= storyRepository.findById(dto.getId()).get();
		story.setHighlighted(true);
		storyRepository.save(story);
	}

	private StoryInfoDTO toStoryInfoDTO(Story story, String url) {
		StoryInfoDTO result = new StoryInfoDTO();
		result.setId(story.getId());
		result.setCreated(story.getMedia().getCreated());
		result.setUsername(story.getMedia().getUsername());
		result.setUrl(url);
		return result;
	}
}
