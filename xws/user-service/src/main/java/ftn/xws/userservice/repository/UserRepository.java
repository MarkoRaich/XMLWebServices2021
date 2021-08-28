package ftn.xws.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ftn.xws.userservice.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findOneByUsername(String username);

	User findByUsername(String email);

	User findByAccountName(String accountName);

	User findOneByAccountName(String accountName);

}
