package ftn.xws.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ftn.xws.userservice.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

	Admin findOneByUsername(String username);

	Admin findByUsername(String email);

	


}