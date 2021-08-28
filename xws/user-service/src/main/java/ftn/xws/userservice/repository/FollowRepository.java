package ftn.xws.userservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ftn.xws.userservice.model.Follow;
import ftn.xws.userservice.model.User;

public interface FollowRepository extends JpaRepository<Follow, Long> {


	List<Follow> findByUser(User user);

	List<Follow> findByFollower(User user);

	
	
	List<Follow> findByFollower_UsernameAndMutedIsTrue(String username);
	
	List<Follow> findByFollower_UsernameAndBlockedIsTrue(String username);

	Follow findFirstByUserAndFollower(User pMuted, User pMutedBy);


}
