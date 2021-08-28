package ftn.xws.userservice.service.Impl;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.xws.userservice.model.Follow;
import ftn.xws.userservice.model.User;
import ftn.xws.userservice.repository.FollowRepository;
import ftn.xws.userservice.repository.UserRepository;
import ftn.xws.userservice.service.UserInteractionService;

@Service
public class UserInteractionServiceImpl implements UserInteractionService {
	
	private final FollowRepository followRepository;
    private final UserRepository userRepository;
    
    @Autowired
    public UserInteractionServiceImpl(FollowRepository followRepository, UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void updateMute(String muted, String mutedBy) {
        User pMuted = userRepository.findOneByUsername(muted);
        User pMutedBy = userRepository.findOneByUsername(mutedBy);
        Follow interaction = followRepository.findFirstByUserAndFollower(pMuted, pMutedBy);
        if(interaction == null)
            throw new IllegalArgumentException("Profiles must be following one another before muting");
        else
            interaction.setMuted(!interaction.isMuted());
        followRepository.save(interaction);
    }

	@Override
    public void updateBlock(String blocked, String blockedBy) {
        User pBlocked = userRepository.findOneByUsername(blocked);
        User pBlockedBy = userRepository.findOneByUsername(blockedBy);
        Follow interaction = followRepository.findFirstByUserAndFollower(pBlocked, pBlockedBy);
        if(interaction == null)
            interaction = new Follow(pBlocked, pBlockedBy, false, false, true);
        else
            interaction.setBlocked(!interaction.isBlocked());
        followRepository.save(interaction);
    }

	@Override
	public Collection<String> getMutedProfiles(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getBlockedProfiles(String username) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//    public Collection<String> getMutedProfiles(String username) {
//        return followRepository.findByFollowedBy_RegularUserUsernameAndMutedIsTrue(username).stream()
//                .map(f -> f.getUser().getUsername())
//                .collect(Collectors.toList());
//    }
//
//	@Override
//    public Collection<String> getBlockedProfiles(String username) {
//        return followRepository.findByFollowedBy_RegularUserUsernameAndBlockedIsTrue(username).stream()
//                .map(f -> f.getUser().getUsername())
//                .collect(Collectors.toList());
//    }

}
