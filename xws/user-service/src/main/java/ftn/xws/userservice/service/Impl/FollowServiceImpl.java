package ftn.xws.userservice.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.xws.userservice.model.Follow;
import ftn.xws.userservice.repository.FollowRepository;
import ftn.xws.userservice.service.FollowService;

import java.util.Optional;

@Service
public class FollowServiceImpl implements FollowService {

	@Autowired
    private FollowRepository followRepository;


    @Override
    public String getFollowedBy(long followId) {
        Optional<Follow> follow = followRepository.findById(followId);
        if(follow.isPresent())
            return follow.get().getFollower().getUsername();
        else
            return null;
    }
}
