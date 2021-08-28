package ftn.xws.contentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.xws.contentservice.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
