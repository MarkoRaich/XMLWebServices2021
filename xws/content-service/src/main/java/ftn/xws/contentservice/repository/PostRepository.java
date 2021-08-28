package ftn.xws.contentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.xws.contentservice.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	
	Post findOneById(long id);
}
