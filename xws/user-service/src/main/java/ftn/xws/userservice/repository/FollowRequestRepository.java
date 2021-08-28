package ftn.xws.userservice.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import ftn.xws.userservice.model.FollowRequest;
import ftn.xws.userservice.model.User;

public interface FollowRequestRepository extends JpaRepository<FollowRequest, Long> {
	Set<FollowRequest> findByUser(User profile);
}
