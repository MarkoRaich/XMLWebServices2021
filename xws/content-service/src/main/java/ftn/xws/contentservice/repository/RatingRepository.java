package ftn.xws.contentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.xws.contentservice.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long>{

}
