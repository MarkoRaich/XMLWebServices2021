package ftn.xws.contentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.xws.contentservice.model.Collection;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long>{

}
