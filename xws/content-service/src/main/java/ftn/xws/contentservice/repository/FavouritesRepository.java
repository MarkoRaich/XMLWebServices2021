package ftn.xws.contentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.xws.contentservice.model.Favourites;
import ftn.xws.contentservice.model.Post;

@Repository
public interface FavouritesRepository extends JpaRepository<Favourites, Long> {
	Favourites findByPost(Post post);
}
