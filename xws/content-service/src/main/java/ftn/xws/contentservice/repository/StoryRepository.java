package ftn.xws.contentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.xws.contentservice.model.Story;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long>{

}
