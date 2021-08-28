package ftn.xws.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ftn.xws.userservice.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long>{

	Authority findByName(String name);

}
