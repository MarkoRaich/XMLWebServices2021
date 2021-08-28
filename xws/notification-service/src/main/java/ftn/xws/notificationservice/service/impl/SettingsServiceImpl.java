package ftn.xws.notificationservice.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.xws.notificationservice.model.NotificationSettingsPerFollow;
import ftn.xws.notificationservice.model.NotificationSettingsPerProfile;
import ftn.xws.notificationservice.repository.SettingsPerFollowRepository;
import ftn.xws.notificationservice.repository.SettingsPerProfileRepository;
import ftn.xws.notificationservice.service.FollowService;
import ftn.xws.notificationservice.service.SettingsService;

@Service
public class SettingsServiceImpl implements SettingsService {
	@Autowired
	private SettingsPerProfileRepository settingsPerProfileRepository;
	@Autowired
	private SettingsPerFollowRepository settingsPerFollowRepository;
    //@Autowired
	//private FollowService followService;

    
    

	@Override
    public void createSettingsPerProfile(String profile) {
        if(!settingsPerProfileRepository.findByProfile(profile).isPresent())
            settingsPerProfileRepository.save(new NotificationSettingsPerProfile((profile)));
    }

	@Override
    public void createSettingsPerFollow(long followId, String profile) {
        if(!settingsPerFollowRepository.findByFollowId(followId).isPresent())
            settingsPerFollowRepository.save(new NotificationSettingsPerFollow(followId, profile));
    }

	@Override
    public void deleteSettingsPerFollow(long followId) {
        Optional<NotificationSettingsPerFollow> settings = settingsPerFollowRepository.findByFollowId(followId);
        if(settings.isPresent())
            settingsPerFollowRepository.delete(settings.get());
    }

	 @Override
	 public void update(NotificationSettingsPerProfile settings, String requestedBy) {
	        if(!hasAuthorityToUpdate(settings, requestedBy))
	            return;;
	        Optional<NotificationSettingsPerProfile> original = settingsPerProfileRepository
	                .findByProfile((settings.getProfile()));
	        if(original.isPresent()) {
	            settings.setId(original.get().getId());
	            settings.setProfile(original.get().getProfile());
	            settingsPerProfileRepository.save(settings);
	        }
	    }


	 @Override
	 public void update(NotificationSettingsPerFollow settings, String requestedBy) {
	        /*if(!hasAuthorityToUpdate(settings, requestedBy))
	            return;;*/
	        Optional<NotificationSettingsPerFollow> original = settingsPerFollowRepository
	                .findByFollowId(settings.getFollowId());
	        if(original.isPresent()) {
	            settings.setId(original.get().getId());
	            settings.setProfile(original.get().getProfile());
	            settingsPerFollowRepository.save(settings);
	        }
	 }

	 @Override
	    public List<NotificationSettingsPerFollow> getByProfile(String profile) {
	        return settingsPerFollowRepository.findAll().stream()
	               // .filter(s -> followService.getProfileByFollow(s.getFollowId()).equals(profile))
	                .collect(Collectors.toList());
	    }

	@Override
    public NotificationSettingsPerProfile get(String profile) {
        return settingsPerProfileRepository.findByProfile(profile).orElse(null);
    }

	public boolean hasAuthorityToUpdate(NotificationSettingsPerProfile settings, String requestedBy) {
        return settings.getProfile().equals(requestedBy);
    }

    public boolean hasAuthorityToUpdate(NotificationSettingsPerFollow settings, String requestedBy) {
        String profile = "nesto";//followService.getProfileByFollow(settings.getFollowId());
        return profile != null && profile.equals(requestedBy);
    }
}
